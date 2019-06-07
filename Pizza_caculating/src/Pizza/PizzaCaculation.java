package Pizza;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Spycsh
 *
 */
public class PizzaCaculation {
	// static initialization

	// create a toppingName bonding price map
	private static HashMap<String, Double> ToppingMap;
	static {
		ToppingMap = new HashMap<String, Double>();
		ToppingMap.put("chilly", 0.1);
		ToppingMap.put("cheese", 0.2);
		ToppingMap.put("cucumber", 0.5);
		ToppingMap.put("currywurst", 0.6);
		ToppingMap.put("beef", 0.9);
		ToppingMap.put("lactose", 0.8);
	}

	JRadioButton pizzaKind1;
	JRadioButton pizzaKind2;
	JRadioButton pizzaKind3;

	// if size is not choosed, we cannot caculate prize
	// this sign will be set False in the caculateSize method
	boolean sizeIsChoosed = true;

	private JCheckBox[] checkBoxes;

	private double totalPrice = 0.0;

	private JTextArea totalPriceField;

	PizzaCaculation() {
		init();
	}

	public double caculateSize() {
		double priceSize = 0.0;
		if (pizzaKind1.isSelected()) {
			priceSize += 4.0;
		} else if (pizzaKind2.isSelected()) {
			priceSize += 5.5;
		} else if (pizzaKind3.isSelected()) {
			priceSize += 7.0;
		} else {
			JOptionPane testOpt = new JOptionPane();
			testOpt.showMessageDialog(new JFrame(), "Warning!No pizza size is choosed!");
			sizeIsChoosed = false;
		}
		return priceSize;
	}

	public double caculateToppings() throws NullPointerException {
		double priceTopping = 0.0;

		for (JCheckBox t : checkBoxes) {
//			System.out.println(t.getText());
			if (t.isSelected()) {
				String temp = t.getText();
				double temp2 = ToppingMap.get(temp);
				priceTopping = priceTopping + temp2;
			}

		}
		System.out.println(priceTopping);
		return priceTopping;
	}

	/**
	 * initialize the whole window
	 * 
	 * @throws NullPointerException
	 */
	private void init() throws NullPointerException {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		f.setSize(600, 400);
		f.setTitle("Pizza caculate");
		f.setLayout(new GridLayout(3, 0)); // 3 rows

		JPanel panel1 = new JPanel(new GridLayout(1, 3)); // 1 row 3 columns
		pizzaKind1 = new JRadioButton("small");
		pizzaKind2 = new JRadioButton("medium");
		pizzaKind3 = new JRadioButton("large");

		panel1.add(pizzaKind1, JRadioButton.CENTER);
		panel1.add(pizzaKind2, JRadioButton.CENTER);
		panel1.add(pizzaKind3, JRadioButton.CENTER);

		ButtonGroup pizzaChoices = new ButtonGroup();
		pizzaChoices.add(pizzaKind1);
		pizzaChoices.add(pizzaKind2);
		pizzaChoices.add(pizzaKind3);

		f.add(panel1);

		JPanel panel2 = new JPanel(new GridLayout(1, 2));
//		JLabel picture = new JLabel(new ImageIcon(".\\a.jpg"));
//		line above will change the original picture to customize
//		to the grid, which is unacceptable

		File originalImg = new File(".\\a.jpg");
		ScaleImage scaleImg = new ScaleImage();
		JLabel picture = null;
		try {
			picture = new JLabel(new ImageIcon(scaleImg.getScaledImage(originalImg)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel2.add(picture);

		JPanel panelTopping = new JPanel(new GridLayout(3, 2)); // 6 topping
		LinkedList<String> boxList = new LinkedList<String>();
		// scalable >_> if you want to add new toppings, just elongate the string
		// add some topping name, not bother if-else at all.
		// OF COURSE you need to add some topping price
		// in the static ToppingMap.
		Collections.addAll(boxList, "chilly cheese cucumber currywurst beef lactose".split(" "));
		JCheckBox checkBoxes[] = new JCheckBox[boxList.size()];
		for (int i = 0; i < boxList.size(); i++) {
			checkBoxes[i] = new JCheckBox(boxList.get(i));
			System.out.println(checkBoxes[i].getText());
			this.checkBoxes = checkBoxes;
			panelTopping.add(checkBoxes[i]);
		}
		panel2.add(panelTopping);
		f.add(panel2);

		JPanel panel3 = new JPanel(new GridLayout(1, 2, 10, 0));
		JButton totalPriceBtn = new JButton("Show Total Price:");
		totalPriceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) throws NullPointerException {
				totalPrice = 0.0;
				// caculate size price
				totalPrice = totalPrice + caculateSize();

				// caculate toppings price
				if (sizeIsChoosed == true) {
					totalPrice += caculateToppings();
//					System.out.println(totalPrice);
					totalPriceField.setText(" " + totalPrice);
				}
			}

		});
		panel3.add(totalPriceBtn);

		totalPriceField = new JTextArea(" " + 0.0);
		panel3.add(totalPriceField);

		f.add(panel3);

		f.setVisible(true);
	}

	public static void main(String[] args) {
		new PizzaCaculation();
	}
}
