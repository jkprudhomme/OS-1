package cs131.pa2.CarsTunnels;

import java.util.*;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class PriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private PriorityQueue<Integer> waiting;
	private HashMap<Vehicle,Tunnel> inside;
	

	public PriorityScheduler(String label, Collection<Tunnel> tunnels, Log log) {
		super(label,log);
		this.tunnels = tunnels;
		waiting = new PriorityQueue<Integer>(Collections.reverseOrder());
		inside = new HashMap<Vehicle,Tunnel>();
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		waiting.add(vehicle.getPriority());
		Boolean added = false;
		while(!added){
			while(vehicle.getPriority() < waiting.peek()){}
				for(Tunnel tunnel:tunnels){
					if(tunnel.tryToEnter(vehicle)){
						inside.put(vehicle, tunnel);
						added = true;
						break;
					}
				}
		}
		System.out.println("finished adding");
		waiting.remove(vehicle.getPriority());
		return true;
		
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
//		inside.remove(vehicle).exitTunnelInner(vehicle);
	}	
}
