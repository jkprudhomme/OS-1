package cs131.pa2.CarsTunnels;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class PreemptivePriorityScheduler extends Tunnel {
	private Collection<Tunnel> tunnels;
	private PriorityQueue<Integer> waiting;
	private HashMap<Vehicle, Tunnel> inside;
	private Set<Tunnel> hasAmb;
	private HashMap<Tunnel,ArrayList<Vehicle>> matches;
	protected final Lock lock = new ReentrantLock();
	protected final Condition isTopPriority = lock.newCondition();
	protected final Condition thereIsAnAmbulance = lock.newCondition();

	public PreemptivePriorityScheduler(String label, Collection<Tunnel> tunnels, Log log) {
		super(label, log);
		this.tunnels = tunnels;
		waiting = new PriorityQueue<Integer>(Collections.reverseOrder());
		inside = new HashMap<Vehicle, Tunnel>();
		hasAmb = new HashSet<Tunnel>();
		matches = new HashMap<Tunnel,ArrayList<Vehicle>>();
		for(Tunnel tunnel: tunnels){
			matches.put(tunnel, new ArrayList<Vehicle>());
		}
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		lock.lock();
		waiting.add(vehicle.getPriority());
		try {
			while ((vehicle.getPriority() < waiting.peek())||tunnelsfull(vehicle)) {
				isTopPriority.await();
			}
			waiting.remove(vehicle.getPriority());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return true;
	}

	private boolean tunnelsfull(Vehicle vehicle) {
		if(vehicle instanceof Ambulance){
			for (Tunnel tunnel : tunnels) {
				if (tunnel.tryToEnter(vehicle) && !hasAmb.contains(tunnel)) {
					hasAmb.add(tunnel);
					inside.put(vehicle, tunnel);
					for(Vehicle normal: matches.get(tunnel)){
						normal.setAmb(true);
					}
					return false;
				}
			}
		}else{
			for (Tunnel tunnel : tunnels) {
				if (tunnel.tryToEnter(vehicle) && !hasAmb.contains(tunnel)) {
					inside.put(vehicle, tunnel);
					vehicle.addTunnel(tunnel);
					matches.get(tunnel).add(vehicle);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
		lock.lock();
		try {
			Tunnel tunnel = inside.get(vehicle);
			tunnel.exitTunnel(vehicle);
//			System.out.println(vehicle+" was removed");
			if (vehicle instanceof Ambulance) {
				for(Vehicle normal: matches.get(tunnel)){
					normal.setAmb(false);
					normal.signalAll();
				}
				hasAmb.remove(tunnel);
//				vehicle.signalAll(); // Made a signalAll method in Vehicle to try and signal from outside the class
				isTopPriority.signalAll();
			}else{
				if(!hasAmb.contains(inside.get(vehicle))) {
					matches.get(tunnel).remove(vehicle);
					inside.remove(vehicle);
				}
			}
		} finally {
			lock.unlock();
		}
	}
}
