package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.*;

public class BasicTunnel extends Tunnel{

//	keeps track of number of cars in tunnel, if there is a sled in the tunnel, 
//	and the current direction for vehicles in the tunnel
	
	private int cars;
	private Boolean sled;
	private Boolean amb;
	private Direction dir;
	
	public BasicTunnel(String name) {
		super(name);
		cars = 0;
		sled = false;
		dir = null;
		amb = false;
	}

	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
//		checks for type of vehicle and then checks variables to see if conditions are met for vehicle to enter
		if(vehicle instanceof Car){
			if(cars < 3 && !sled && !amb){
				if(cars>0){
					if(vehicle.getDirection() == dir){
//						update number of cars
						cars++;
						return true;
					}
				}else{
//					update number of cars and direction
					cars++;
					dir = vehicle.getDirection();
					return true;
				}
			}else{
				return false;
			}
		}else if(vehicle instanceof Sled){
			if(cars == 0 && !sled && !amb){
//				update sled boolean
				sled = true;
				return true;
			}else{
				return false;
			}
		}else if(vehicle instanceof Ambulance){
			amb = true;
			return true;
		}
		return false;
	}

	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
//		check type of vehicle and then update variables as necessary
		if(vehicle instanceof Car){
			cars--;
		}else if(vehicle instanceof Sled){
			sled = false;
		}else if(vehicle instanceof Ambulance){
			amb = false;
		}
		
	}
	
//	public Boolean getAmb(){
//		return amb;
//	}
	
}
