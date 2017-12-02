package window;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import listener.ExitListener;
import model.Reader;
import model.Writer;

import r.R;

public class MainWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;

	private JTextArea txt_event;
	private JLabel num_writer;
	private JLabel num_reader;
	private JLabel who_operate;
	private JButton exit;

	int type;
	int writer_num;
	int reader_num;
	int min;
	int max;

	public MainWindow(int type, int writer_num, int reader_num, int min, int max) {
		this.type = type;
		this.writer_num = writer_num;
		this.reader_num = reader_num;
		this.min = min;
		this.max = max;
	}

	protected void addListener() {
		exit.addActionListener(new ExitListener());
	}

	protected void regitstComponent() {
		R.getInstance().registObject("txt_event", txt_event);
		R.getInstance().registObject("num_write", num_writer);
		R.getInstance().registObject("num_reader", num_reader);
		R.getInstance().registObject("who_operate", who_operate);
	}

	protected void init() {
		Font font = new Font("����", 1, 20);

		txt_event = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(txt_event);
		add(scrollpane);
		txt_event.setFont(font);
		// txt_event.setFixedCellHeight(24);

		num_writer = new JLabel("д�ߣ�" + writer_num + " ��");
		num_writer.setFont(font);
		add(num_writer);
		num_reader = new JLabel("���ߣ�" + reader_num + " ��");
		num_reader.setFont(font);
		add(num_reader);

		who_operate = new JLabel("XXX ���ڲ����ٽ���Դ");
		who_operate.setFont(font);
		// add(who_operate);

		exit = new JButton("�˳�");
		exit.setFont(font);
		add(exit);

		scrollpane.setBounds(0, 0, 1000, 450);
		num_writer.setBounds(0, 470, 110, 30);
		num_reader.setBounds(120, 470, 110, 30);
		who_operate.setBounds(0, 510, 1000, 30);
		exit.setBounds(480, 510, 80, 40);

		this.validate();
	}

	public void startTask() {
		for (int i = 0; i < writer_num; ++i) {
			new Writer("д��" + i, type, min, max).start();
			if (i < reader_num)
				new Reader("����" + i, type, min, max).start();
		}
	}

	@Override
	protected void initWindow() {
		super.initWindow();

		setTitle("�߳�ͬ����ʽʵ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رյ�Ĭ�ϲ������˳�����

		setLayout(null);// ����������Ϊ�ղ���
	}

}
