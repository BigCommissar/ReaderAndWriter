package model;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JTextArea;

import r.R;

public abstract class Command {

	int type; // ͬ����ʽ������
	String name; // ��/д�ߵ�����
	JTextArea textarea; // �����־���ı���
	Random random;
	int min; // ����ʱ�����������Сֵ
	int max; // ���ֵ

	public Command(String name, int type, int min, int max) {
		this.name = name;
		this.type = type;

		textarea = (JTextArea) R.getInstance().getObject("txt_event");
		this.min = min;
		this.max = max;
		random = new Random();
	}

	// ��ȡһ����ΧΪ[min,max]�������
	private int getRandom(int min, int max) {
		int service = random.nextInt();
		if (service < 0)
			service *= -1;
		service = service % (max - min + 1) + min;
		return service;
	}

	public void start() {
		// �����߳�������ѭ�����ȴ������ٽ����������ٽ���Դ�������Բ��Ϻ�ʱ��ģ������ļ������˳��ٽ���
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					// �������
					if (type == 0) { // 0 �Ƕ�������
						readFirstEnter();
					} else if (type == 1) { // 1 �Ƕ�д�߹�ƽ
						fairEnter();
					}

					// ����һ����ΧΪ[min,max]�������service��Ȼ����ѯserviece�룬ģ������ٽ���Դ
					int service = getRandom(min, max);

					printLog(name + " �����ٽ�����׼�������ٽ���Դ����Ҫ " + service + " ��");

					long after = System.currentTimeMillis() + service * 1000;
					while (System.currentTimeMillis() < after)
						;

					printLog(name + " ����ٽ���Դ�ķ��ʣ����� " + service + " ����");

					// ׼���˳�
					if (type == 0) { // 0 �Ƕ�������
						readFirstLeave();
					} else if (type == 1) { // 1 �Ƕ�д�߹�ƽ
						fairLeave();
					}

					// Ϊ�˷�ֹ��д�߼������������߼����Ӻ����Ŷӵȴ�
					try {
						int sleep_time = getRandom(min / 2 + 1, max / 2 + 1);
						printLog(name + " ��Ϣ " + sleep_time + " ����");
						Thread.sleep(sleep_time*1000);
					} catch (InterruptedException e) {
						printLog("sleep error");
					}
				}
			}

		}).start();
	}

	// �����ĸ�������ͬ����ʽ������˳��ľ�����룬��������ʵ��
	protected abstract void readFirstEnter();

	protected abstract void fairEnter();

	protected abstract void readFirstLeave();

	protected abstract void fairLeave();
	
	// ���������������Ļ���һ����Ϣ
	protected synchronized void printLog(String log) {
		textarea.append(log + "\n");
		textarea.setCaretPosition(textarea.getText().length());
	}

	// ������ͬ��������ź����Ĳ���
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
