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
	private Boolean amb = false; 
	protected final Lock lock = new ReentrantLock();
	protected final Condition isTopPriority = lock.newCondition();
	protected final Condition thereIsAnAmbulance = lock.newCondition();

	public PreemptivePriorityScheduler(String label, Collection<Tunnel> tunnels, Log log) {
		super(label, log);
		this.tunnels = tunnels;
		waiting = new PriorityQueue<Integer>(Collections.reverseOrder());
		inside = new HashMap<Vehicle, Tunnel>();
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		lock.lock();
		waiting.add(vehicle.getPriority());
		try {
			while (tunnelsfull(vehicle) || vehicle.getPriority() < waiting.peek()) {
				isTopPriority.await();
			}
			if (vehicle instanceof Ambulance) {
				amb = true;//same problem, is this shared among the vehicles in the tunnel or not is not clear
			} else {
				while (amb) {
					thereIsAnAmbulance.await();
				}
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
		for (Tunnel tunnel : tunnels) {
			if (tunnel.tryToEnter(vehicle)) {
				inside.put(vehicle, tunnel);
				System.out.println(vehicle.getName() + " is in " + tunnel.getName());
				return false;
			}
		}
		return true;
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
		lock.lock();
		try {
			inside.remove(vehicle).exitTunnel(vehicle);
			System.out.println(vehicle.getName() + " was removed");
			if (vehicle instanceof Ambulance) {
				amb = false;
				thereIsAnAmbulance.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
}
