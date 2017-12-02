package model;

import java.util.concurrent.Semaphore;

public class Reader extends Command {
	
	Semaphore sem_mutex;
	Semaphore sem_reader;
	
	Semaphore sem_fair;

	public Reader(String name, int type, int min, int max) {
		super(name, type, min, max);
		
		sem_mutex=getSemaphore("mutex");
		sem_reader=getSemaphore("reader");
		sem_fair=getSemaphore("fair");
	}

	@Override
	protected void readFirstEnter() {
		P(sem_reader);

		if(reader_shared_count == 0){
			P(sem_mutex);
		}
		++reader_shared_count;
		
		V(sem_reader);
	}

	@Override
	protected void fairEnter() {
		P(sem_fair);
		P(sem_reader);

		if(reader_shared_count == 0){
			P(sem_mutex);
		}
		++reader_shared_count;
		
		V(sem_reader);
		V(sem_fair);
	}

	@Override
	protected void readFirstLeave() {
		P(sem_reader);

		--reader_shared_count;
		if(reader_shared_count == 0){
			V(sem_mutex);
		}
		
		V(sem_reader);
	}

	@Override
	protected void fairLeave() {
		P(sem_reader);

		--reader_shared_count;
		if(reader_shared_count == 0){
			V(sem_mutex);
		}
		
		V(sem_reader);
	}
	
}
