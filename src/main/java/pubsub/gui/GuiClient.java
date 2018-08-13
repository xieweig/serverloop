package pubsub.gui;

import pubsub.Global;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class GuiClient extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton jButton = new JButton("produce"){{
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String product = "request from 192.168.10."+Global.random.nextInt(256);

                try {
                    Global.requests.put(product);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }};


    public GuiClient() throws HeadlessException {

        this.init();
        this.add(jButton);

        this.setVisible(true);
    }
    public void init() {

        this.setSize(400, 400);
        this.setTitle("====producer====exam====");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }



}
