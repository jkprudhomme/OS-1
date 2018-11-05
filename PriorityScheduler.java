package cs131.pa2.CarsTunnels;

import java.util.*;
import java.util.concurrent.locks.*;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class PriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private PriorityQueue<Integer> waiting;
	private HashMap<Vehicle,Tunnel> inside;
	
	private final Lock lock = new ReentrantLock();
	private final Condition topPriority = lock.newCondition();
	

	public PriorityScheduler(String label, Collection<Tunnel> tunnels, Log log) {
		super(label,log);
		this.tunnels = tunnels;
		waiting = new PriorityQueue<Integer>(Collections.reverseOrder());
		inside = new HashMap<Vehicle,Tunnel>();
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		lock.lock();
		waiting.add(vehicle.getPriority());
		Boolean added = false;
		while(!added){
			while(vehicle.getPriority() < waiting.peek()){
				try{
					topPriority.await();
				}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Tunnel tunnel:tunnels){
				if(tunnel.tryToEnter(vehicle)){
					inside.put(vehicle, tunnel);
					added = true;
					waiting.remove(vehicle.getPriority());
					topPriority.signalAll();
					lock.unlock();
					return true;
				}
			}
			try{
				topPriority.await();
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		topPriority.signalAll();
//		lock.unlock();
		return true;
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
		lock.lock();
		inside.remove(vehicle).exitTunnelInner(vehicle);
		topPriority.signalAll();
		lock.unlock();
	}	
}
