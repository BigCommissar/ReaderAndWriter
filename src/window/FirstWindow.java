package window;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import r.R;

import listener.EnterListener;

public class FirstWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> combo;
	private JTextField writer_num;
	private JTextField reader_num;
	private JTextField min;
	private JTextField max;
	private JButton enter;

	@Override
	protected void addListener() {
		enter.addActionListener(new EnterListener());
	}

	@Override
	protected void regitstComponent() {
		R r = R.getInstance();
		r.registObject("combobox", combo);
		r.registObject("writer_num", writer_num);
		r.registObject("reader_num", reader_num);
		r.registObject("min", min);
		r.registObject("max", max);
		r.registObject("firstwindow", this);
	}

	@Override
	protected void init() {
		Font font = new Font("宋体", 1, 20);

		JLabel type = new JLabel("请设置同步方式：");
		type.setFont(font);
		combo = new JComboBox<String>();
		combo.addItem("读者优先");
		combo.addItem("读写者公平");
		combo.setFont(font);
		add(type);
		add(combo);

		JLabel label_writer = new JLabel("请设置写者数目：");
		label_writer.setFont(font);
		writer_num = new JTextField("5");
		writer_num.setFont(font);
		add(label_writer);
		add(writer_num);

		JLabel label_reader = new JLabel("请设置读者数目：");
		reader_num = new JTextField("5");
		label_reader.setFont(font);
		reader_num.setFont(font);
		add(label_reader);
		add(reader_num);

		JLabel service_time = new JLabel("请设置服务时间：");
		JLabel sperator = new JLabel("-");
		sperator.setFont(font);
		service_time.setFont(font);
		min = new JTextField("3");
		max = new JTextField("50");
		min.setFont(font);
		max.setFont(font);
		add(service_time);
		add(sperator);
		add(min);
		add(max);

		enter = new JButton("确定");
		enter.setFont(font);
		add(enter);

		type.setBounds(0, 0, 180, 25);
		combo.setBounds(0, 26, 140, 25);

		label_writer.setBounds(0, 71, 180, 25);
		writer_num.setBounds(0, 96, 140, 25);

		label_reader.setBounds(0, 141, 180, 25);
		reader_num.setBounds(0, 166, 140, 25);

		service_time.setBounds(200, 0, 180, 25);
		min.setBounds(200, 26, 40, 25);
		sperator.setBounds(245, 26, 20, 25);
		max.setBounds(260, 26, 40, 25);

		enter.setBounds(200, 120, 80, 40);

		this.validate();
	}

	@Override
	protected void initWindow() {
		super.initWindow();

		setSize(500, 300);

		setTitle("线程同步方式实验");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭的默认操作：退出程序

		setLayout(null);// 将界面设置为空布局
	}

}
