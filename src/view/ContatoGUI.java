package view;

import controller.ContatoController;
import helper.foneHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Contato;

public class ContatoGUI extends JFrame {
    
    private JTextField tfPesquisar;
    private JButton btNew, btEdit, btDelete;
    private JTable tbContatos;
    private DefaultTableModel model;
    private JScrollPane scroll;

    public ContatoGUI() {
        setConponents();
        setEvents();        
    }

    private void setConponents() {
        setLayout(null);
        setTitle("Agenda");
        setResizable(false);
        setIconImage(new ImageIcon("images/icons/contatos.png").getImage());
        setBounds(0,0,400,400);
        
        tfPesquisar = new JTextField();
        tfPesquisar.setBounds(10,10,200,32);
        add(tfPesquisar);
        btNew = new JButton(new ImageIcon("images/icons/add.png"));
        btNew.setBackground(new Color(238,238,238));
        btNew.setBorder(null);
        btNew.setBounds(250,10,32,32);
        add(btNew);
        btEdit = new JButton(new ImageIcon("images/icons/edit.png"));
        btEdit.setBounds(300,10,32,32);
        btEdit.setBackground(new Color(238,238,238));
        btEdit.setBorder(null);
        add(btEdit);
        btDelete = new JButton(new ImageIcon("images/icons/del.png"));
        btDelete.setBackground(new Color(238,238,238));
        btDelete.setBorder(null);
        btDelete.setBounds(350,10,32,32);
        add(btDelete);
        model = new DefaultTableModel(new Object[]{
            "ID","NOME","TELEFONE"        
        },0); 
        loadTable();
        tbContatos = new JTable(model);
        tbContatos.setRowHeight(30);        
        tbContatos.getColumnModel().getColumn(0).setPreferredWidth(5);
        tbContatos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbContatos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbContatos.getColumnModel().getColumn(0).setResizable(false);
        tbContatos.getColumnModel().getColumn(1).setResizable(false);
        tbContatos.getColumnModel().getColumn(2).setResizable(false); 
        tbContatos.getTableHeader().setReorderingAllowed(false);
        scroll = new JScrollPane();
        scroll.setViewportView(tbContatos);
        scroll.setBounds(10,70,375,280);
        add(scroll);
    }

    private void setEvents() {
        btEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i[] = tbContatos.getSelectedRows();
                if(i.length == 0){
                    JOptionPane.showMessageDialog(null, "Selecione um contato!");
                }else if(i.length > 1){
                    JOptionPane.showMessageDialog(null, "Selecione apenas um contato!");
                }else{
                    Contato contato = new Contato();
                    contato.setId ((int) tbContatos.getValueAt(i[0], 0));
                    contato.setNome((String) tbContatos.getValueAt(i[0], 1));
                    contato.setFone((String) tbContatos.getValueAt(i[0], 2));
                    FormContatoUI form = new FormContatoUI(contato, model);
                    form.open();
                }
            }
        });
        btNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            FormContatoUI form = new FormContatoUI(null, model);
            form.open();
            }
        });
        btDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               int i[] = tbContatos.getSelectedRows();
                if(i.length == 0){
                    JOptionPane.showMessageDialog(null, "Selecione um contato!");
                }else if(i.length > 1){
                    JOptionPane.showMessageDialog(null, "Selecione apenas um contato!");
                }else{
                    Contato contato = new Contato();
                    contato.setId ((int) tbContatos.getValueAt(i[0], 0));
                    contato.setNome((String) tbContatos.getValueAt(i[0], 1));
                    contato.setFone((String) tbContatos.getValueAt(i[0], 2));
                    int resposta = JOptionPane.showConfirmDialog(null, "Deseja Excluir "+ contato.getNome()+ "?");
                    if(resposta == 0){
                        if(new ContatoController().remover(contato.getId())){
                            JOptionPane.showMessageDialog(null, contato.getNome() + "Excluido com sucesso!");
                            loadTable();
                        }
                    }else{
                    JOptionPane.showMessageDialog(null, "Falha ao tentar excluir");
                    }
                }
            }
        });
        tfPesquisar.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                model.setRowCount(0);
                for(Contato contato : new ContatoController().listar(tfPesquisar.getText())){
                    model.addRow(new Object[]{contato.getId(), contato.getNome(), foneHelper.formatar(contato.getFone())});
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }
    private void loadTable(){
        model.setRowCount(0);
        for(Contato contato: new ContatoController().listar(null)){
        model.addRow(new Object[] {contato.getId(),contato.getNome(), foneHelper.formatar (contato.getFone())});
        }
    }
    
    public static void main(String[] args) {
        ContatoGUI frame = new ContatoGUI();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (window.width - frame.getSize().width)/2;
        int y = (window.height - frame.getSize().height)/2;
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    
}
