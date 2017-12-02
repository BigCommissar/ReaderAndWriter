package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import r.R;
import window.FirstWindow;
import window.MainWindow;

public class EnterListener implements ActionListener {

	private JComboBox<String> combo;
	private JTextField writer_num;
	private JTextField reader_num;
	private JTextField min;
	private JTextField max;

	@SuppressWarnings("unchecked")
	public EnterListener() {
		R r = R.getInstance();
		combo = (JComboBox<String>) r.getObject("combobox");
		writer_num = (JTextField) r.getObject("writer_num");
		reader_num = (JTextField) r.getObject("reader_num");
		min = (JTextField) r.getObject("min");
		max = (JTextField) r.getObject("max");
	}

	boolean isLegalInput(String txt, int start, int end) {
		try {
			int n = Integer.parseInt(txt);

			if (n >= start && n <= end)
				return true;
			else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if (!isLegalInput(writer_num.getText(), 1, 10)) {
			JOptionPane.showMessageDialog(null, "写者数目错误，请输入 1 到 10 之间的数！",
					"错误", JOptionPane.ERROR_MESSAGE);
		} else if (!isLegalInput(reader_num.getText(), 1, 10)) {
			JOptionPane.showMessageDialog(null, "读者数目错误，请输入 1 到 10 之间的数！",
					"错误", JOptionPane.ERROR_MESSAGE);
		} else if (!isLegalInput(min.getText(), 1, 100)) {
			JOptionPane.showMessageDialog(null, "服务时间最小值错误，请输入 1 到 100 之间的数！",
					"错误", JOptionPane.ERROR_MESSAGE);
		} else if (!isLegalInput(max.getText(), 1, 100)) {
			JOptionPane.showMessageDialog(null, "服务时间最大值错误，请输入 1 到 100 之间的数！",
					"错误", JOptionPane.ERROR_MESSAGE);
		} else if (Integer.parseInt(min.getText()) > Integer.parseInt(max
				.getText())) {
			JOptionPane.showMessageDialog(null, "服务时间最小值要小于或等于服务时间最大值！", "错误",
					JOptionPane.ERROR_MESSAGE);
		} else {
			FirstWindow window = (FirstWindow) R.getInstance().getObject(
					"firstwindow");
			window.dispose();

			int type = combo.getSelectedIndex();
			int writer = Integer.parseInt(writer_num.getText());
			int reader = Integer.parseInt(reader_num.getText());
			int minimum = Integer.parseInt(min.getText());
			int maximum = Integer.parseInt(max.getText());
			
			MainWindow mainWindow = new MainWindow(type, writer, reader, minimum, maximum);
			mainWindow.run();				// 初始化并打开界面
			mainWindow.startTask();			// 界面准备好了，开始读写任务
		}
	}

}
