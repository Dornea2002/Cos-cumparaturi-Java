import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JLabel titlu;

    private JLabel codProdusLabel;
    private JLabel denumireProdusLabel;
    private JLabel pretProdusLabel;
    private JLabel glutenFreeLabel;

    private JTextField codProdusText;
    private JTextField denumireProdusText;
    private JTextField pretProdusText;
    private JCheckBox glutenFreeCheckBox;

    private JButton adaugaCosButton;
    private JButton stergeCosButton;

    private JLabel cosLabel;
    private JList<Produs> cosList;
    private JButton totalCosButton;

    private JLabel bandaLabel;
    private JButton adaugaBandaButton;
    private JList<Produs> bandaList;

    private JButton totalBandaButton;
    private JCheckBox discountCheckBox;

    private ArrayList<Produs> poduseAdaugateCosList=new ArrayList<Produs>();
    private ArrayList<Produs> poduseAdaugateBandaList=new ArrayList<Produs>();

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        titlu = new JLabel();
        titlu.setText("Cumparaturi");
        titlu.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        titlu.setBounds(350, 50, 2000, 20);
        getContentPane().add(titlu);

        codProdusLabel = new JLabel();
        codProdusLabel.setText("Cod Produs:");
        codProdusLabel.setBounds(20, 100, 100, 20);
        getContentPane().add(codProdusLabel);

        codProdusText = new JTextField();
        codProdusText.setBounds(20, 125, 100, 20);
        getContentPane().add(codProdusText);

        denumireProdusLabel = new JLabel();
        denumireProdusLabel.setText("Denumire Produs:");
        denumireProdusLabel.setBounds(200, 100, 150, 20);
        getContentPane().add(denumireProdusLabel);

        denumireProdusText = new JTextField();
        denumireProdusText.setBounds(200, 125, 100, 20);
        getContentPane().add(denumireProdusText);

        pretProdusLabel = new JLabel();
        pretProdusLabel.setText("Pret Produs:");
        pretProdusLabel.setBounds(400, 100, 150, 20);
        getContentPane().add(pretProdusLabel);

        pretProdusText = new JTextField();
        pretProdusText.setBounds(400, 125, 100, 20);
        getContentPane().add(pretProdusText);

        glutenFreeLabel = new JLabel();
        glutenFreeLabel.setText("GlutenFree?");
        glutenFreeLabel.setBounds(600, 100, 150, 20);
        getContentPane().add(glutenFreeLabel);

        glutenFreeCheckBox = new JCheckBox();
        glutenFreeCheckBox.setText("Fara Gluten");
        glutenFreeCheckBox.setBounds(600, 125, 100, 20);
        getContentPane().add(glutenFreeCheckBox);

        adaugaCosButton=new JButton();
        adaugaCosButton.setText("Adauga produs in cos");
        adaugaCosButton.setBounds(100, 180, 200, 40);
        adaugaCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cod=Integer.parseInt(codProdusText.getText());
                String denumire=denumireProdusText.getText();
                double pret=Double.parseDouble(pretProdusText.getText());
                boolean gluten=glutenFreeCheckBox.isSelected();
                Produs produs= new Produs(cod, denumire, pret, gluten);
                poduseAdaugateCosList.add(produs);
                rewriteProduseList(poduseAdaugateCosList, cosList);
                codProdusText.setText("");
                denumireProdusText.setText("");
                pretProdusText.setText("");
                glutenFreeCheckBox.setSelected(false);
            }
        });
        getContentPane().add(adaugaCosButton);

        stergeCosButton=new JButton();
        stergeCosButton.setText("Sterge produs in cos");
        stergeCosButton.setBounds(450, 180, 200, 40);
        stergeCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cod= Integer.parseInt(JOptionPane.showInputDialog("Introduceti codul produsului pe care doriti sa il stergeti:"));
                try {
                    Produs produs = new Produs();
                    produs = produs.cautaProdus(poduseAdaugateCosList, cod);
                    if (produs != null) {
                        poduseAdaugateCosList.remove(produs);
                        rewriteProduseList(poduseAdaugateCosList, cosList);
                    } else throw new Exception("Produsul cu codul " + cod + " nu exista!");
                }
                catch(Exception exp) {
                    JOptionPane.showMessageDialog(null, exp.getMessage());
                }
            }
        });
        getContentPane().add(stergeCosButton);

        cosLabel = new JLabel();
        cosLabel.setText("Cos de cumparaturi");
        cosLabel.setFont(new Font(Font.DIALOG, Font.ITALIC+Font.BOLD, 16));
        cosLabel.setBounds(330, 250, 200, 20);
        getContentPane().add(cosLabel);

        cosList=new JList<Produs>();
        cosList.setBounds(20, 300, 740, 200);
        getContentPane().add(cosList);

        bandaLabel = new JLabel();
        bandaLabel.setText("Banda produse");
        bandaLabel.setBounds(20, 510, 200, 20);
        getContentPane().add(bandaLabel);

        adaugaBandaButton=new JButton();
        adaugaBandaButton.setText("Pune produsul pe banda");
        adaugaBandaButton.setBounds(200, 510, 200, 30);
        getContentPane().add(adaugaBandaButton);
        adaugaBandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=cosList.getSelectedIndex();
                Produs produs=poduseAdaugateCosList.get(index);
                poduseAdaugateBandaList.add(produs);
                rewriteProduseList(poduseAdaugateBandaList, bandaList);
                poduseAdaugateCosList.remove(produs);
                rewriteProduseList(poduseAdaugateCosList, cosList);
            }
        });

        totalCosButton=new JButton();
        totalCosButton.setText("Total cos");
        totalCosButton.setBounds(600, 510, 100, 30);
        totalCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double suma=0;
                for(Produs p:poduseAdaugateCosList){
                    suma+=p.getPret();
                }
                JOptionPane.showMessageDialog(null, "Cosul de cumparaturi costa " + suma + " lei.");
            }
        });
        getContentPane().add(totalCosButton);

        bandaList=new JList<Produs>();
        bandaList.setBounds(20, 550, 500, 100);
        getContentPane().add(bandaList);

        totalBandaButton=new JButton();
        totalBandaButton.setText("Total banda");
        totalBandaButton.setBounds(600, 580, 100, 30);
        totalBandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double suma=0;
                for(Produs p:poduseAdaugateBandaList){
                    suma+=p.getPret();
                }
                if(!discountCheckBox.isSelected())
                    JOptionPane.showMessageDialog(null, "Banda de cumparaturi costa " + suma + " lei.");
                else {
                    suma=suma-0.2*suma;
                    JOptionPane.showMessageDialog(null, "Banda de cumparaturi costa " + suma + " lei.");
                }
            }
        });
        getContentPane().add(totalBandaButton);

        discountCheckBox=new JCheckBox();
        discountCheckBox.setText("20% discount");
        discountCheckBox.setBounds(600, 630, 100, 20);
        getContentPane().add(discountCheckBox);

        this.setSize(800, 700);
        this.setVisible(true);
    }

    public void rewriteProduseList(ArrayList<Produs> listProdus, JList<Produs> list) {
        DefaultListModel<Produs> listModel = new DefaultListModel<Produs>();
        for(Produs p: listProdus) {
            listModel.addElement(p);
        }
        list.setModel(listModel);
    }

}
