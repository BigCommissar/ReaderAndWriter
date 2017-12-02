package model;

import java.util.concurrent.Semaphore;

public class Writer extends Command {
	
	Semaphore sem;
	Semaphore sem_fair;

	public Writer(String name, int type, int min, int max) {
		super(name, type, min, max);
		
		sem = getSemaphore("mutex");//获取全局信号量
		sem_fair=getSemaphore("fair");
	}

	@Override
	protected void readFirstEnter() {
		P(sem);
	}

	@Override
	protected void fairEnter() {
		P(sem_fair);
		P(sem);
		V(sem_fair);
	}

	@Override
	protected void readFirstLeave() {
		V(sem);
	}

	@Override
	protected void fairLeave() {
		V(sem);
	}
}
