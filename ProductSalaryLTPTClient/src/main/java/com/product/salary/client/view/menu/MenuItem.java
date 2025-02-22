/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.salary.client.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuItem extends JPanel {

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	public ArrayList<MenuItem> getSubMenu() {
		return subMenu;
	}

	/**
	 * Creates new form MenuItem
	 */
	private final ArrayList<MenuItem> subMenu = new ArrayList<>();
	private ActionListener act;

	public MenuItem(Icon icon, String menuName, ActionListener act, MenuItem... subMenu) {
		initComponents();
		lbIcon.setIcon(icon);
		lbIcon.setSize(50, 50);
		lbName.setText(menuName);
		lbName.setFont(new Font("Time new romans", Font.PLAIN, 16));
		lbName.setForeground(Color.WHITE);
		setBackground(new Color(8, 92, 255));
		if (act != null) {
			this.act = act;
		}
		this.setSize(new Dimension(Integer.MAX_VALUE, 45));
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 45));
		for (int i = 0; i < subMenu.length; i++) {
			this.subMenu.add(subMenu[i]);
			subMenu[i].setVisible(false);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jSeparator1 = new JSeparator();
		lbIcon = new JLabel();
		lbName = new JLabel();

		setBackground(new Color(255, 255, 255));
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				formMousePressed(evt);
			}
		});

		lbName.setText("Menu Name Here ...");

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSeparator1)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(lbIcon, GroupLayout.PREFERRED_SIZE, 33,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lbName, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(lbName, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
												lbIcon, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jSeparator1,
								GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)));
	}// </editor-fold>//GEN-END:initComponents

	private boolean showing = false;

	private void formMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_formMousePressed
		if (showing) {
			hideMenu();
		} else {
			showMenu();
		}
		if (act != null) {
			act.actionPerformed(null);
		}
	}// GEN-LAST:event_formMousePressed

	private void showMenu() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < subMenu.size(); i++) {
					sleep();
					subMenu.get(i).setVisible(true);
				}
				showing = true;
				getParent().repaint();
				getParent().revalidate();
			}
		}).start();
	}

	private void hideMenu() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = subMenu.size() - 1; i >= 0; i--) {
					sleep();
					subMenu.get(i).setVisible(false);
					subMenu.get(i).hideMenu();
				}
				getParent().repaint();
				getParent().revalidate();
				showing = false;
			}
		}).start();
	}
	public void setIcon(Icon icon) {
		this.lbIcon.setIcon(icon);
	}

	private void sleep() {
		try {
			Thread.sleep(20);
		} catch (Exception e) {
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JSeparator jSeparator1;
	private JLabel lbIcon;
	private JLabel lbName;
	// End of variables declaration//GEN-END:variables
}
