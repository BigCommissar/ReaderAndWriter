package model;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JTextArea;

import r.R;

public abstract class Command {

	int type; // 同步方式的类型
	String name; // 读/写者的名字
	JTextArea textarea; // 输出日志的文本区
	Random random;
	int min; // 服务时间随机数的最小值
	int max; // 最大值

	public Command(String name, int type, int min, int max) {
		this.name = name;
		this.type = type;

		textarea = (JTextArea) R.getInstance().getObject("txt_event");
		this.min = min;
		this.max = max;
		random = new Random();
	}

	// 获取一个范围为[min,max]的随机数
	private int getRandom(int min, int max) {
		int service = random.nextInt();
		if (service < 0)
			service *= -1;
		service = service % (max - min + 1) + min;
		return service;
	}

	public void start() {
		// 在新线程里无限循环，等待进入临界区，操作临界资源（这里以不断耗时间模拟操作文件），退出临界区
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					// 请求进入
					if (type == 0) { // 0 是读者优先
						readFirstEnter();
					} else if (type == 1) { // 1 是读写者公平
						fairEnter();
					}

					// 产生一个范围为[min,max]的随机数service，然后轮询serviece秒，模拟访问临界资源
					int service = getRandom(min, max);

					printLog(name + " 进入临界区，准备访问临界资源，需要 " + service + " 秒");

					long after = System.currentTimeMillis() + service * 1000;
					while (System.currentTimeMillis() < after)
						;

					printLog(name + " 完成临界资源的访问，共计 " + service + " 秒钟");

					// 准备退出
					if (type == 0) { // 0 是读者优先
						readFirstLeave();
					} else if (type == 1) { // 1 是读写者公平
						fairLeave();
					}

					// 为了防止读写者饥饿，让其休眠几秒钟后再排队等待
					try {
						int sleep_time = getRandom(min / 2 + 1, max / 2 + 1);
						printLog(name + " 休息 " + sleep_time + " 秒钟");
						Thread.sleep(sleep_time*1000);
					} catch (InterruptedException e) {
						printLog("sleep error");
					}
				}
			}

		}).start();
	}

	// 以下四个是两种同步方式进入和退出的具体代码，交由子类实现
	protected abstract void readFirstEnter();

	protected abstract void fairEnter();

	protected abstract void readFirstLeave();

	protected abstract void fairLeave();
	
	// 这个方法用于向屏幕输出一条消息
	protected synchronized void printLog(String log) {
		textarea.append(log + "\n");
		textarea.setCaretPosition(textarea.getText().length());
	}

	// 以下是同步所需的信号量的操作
	protected static HashMap<String, Semaphore> map = new HashMap<String, Semaphore>();
	protected static int reader_shared_count = 0;

	protected synchronized Semaphore getSemaphore(String key) {
		if (!map.containsKey(key))
			map.put(key, new Semaphore(1));
		return map.get(key);
	}

	protected synchronized void P(Semaphore sem) {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			printLog("P Error!");
		}
	}

	protected synchronized void V(Semaphore sem) {
		sem.release();
	}
}
