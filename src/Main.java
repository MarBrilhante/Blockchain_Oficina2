import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Blockchain blockchain;
    private static JTextArea blockchainTextArea;
    private static JLabel candidateImageLabel;

    public static void main(String[] args) {
        blockchain = new Blockchain(4);

        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Define a aparência da interface gráfica
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Urna de Votação com Blockchain");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel urnLabel = new JLabel(new ImageIcon("image/urna.png"));
        urnLabel.setBounds(50, 50, 400, 500);
        panel.add(urnLabel);

        candidateImageLabel = new JLabel();
        candidateImageLabel.setBounds(575, 40, 150, 150);
        panel.add(candidateImageLabel);

        JTextField voterIdField = new JTextField();
        voterIdField.setBounds(550, 200, 200, 30);
        panel.add(voterIdField);

        JComboBox<String> candidateComboBox = new JComboBox<>(new String[]{"Candidato A", "Candidato B", "Candidato C"});
        candidateComboBox.setBounds(550, 250, 200, 30);
        candidateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCandidateImage((String) candidateComboBox.getSelectedItem());
            }
        });
        panel.add(candidateComboBox);

        JButton voteButton = new JButton("Votar");
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String voterId = voterIdField.getText();
                String candidate = (String) candidateComboBox.getSelectedItem();
                simulateVoting(voterId, candidate);
                JOptionPane.showMessageDialog(frame, "Seu voto foi registrado com sucesso!");
                updateBlockchainTextArea();
                voterIdField.setText("");
            }
        });
        voteButton.setBounds(550, 300, 200, 40);
        panel.add(voteButton);

        JButton viewDataButton = new JButton("Visualizar Dados");
        viewDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBlockchainData();
            }
        });
        viewDataButton.setBounds(550, 350, 200, 40);
        panel.add(viewDataButton);

        blockchainTextArea = new JTextArea();
        blockchainTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(blockchainTextArea);
        scrollPane.setBounds(50, 400, 400, 150);
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método para simular votação
    private static void simulateVoting(String voterId, String candidate) {
        // Cria um novo bloco representando o voto do eleitor
        Block voteBlock = blockchain.newBlock(voterId, candidate);
        // Adiciona o bloco à blockchain
        blockchain.addBlock(voteBlock);
    }

    // Método para atualizar a área de texto da blockchain
    private static void updateBlockchainTextArea() {
        blockchainTextArea.setText(blockchain.toString());
    }

    // Método para atualizar a imagem do candidato
    private static void updateCandidateImage(String candidate) {
        ImageIcon imageIcon = null;
        switch (candidate) {
            case "Candidato A":
                imageIcon = new ImageIcon("image/candidateA.png");
                break;
            case "Candidato B":
                imageIcon = new ImageIcon("image/candidateB.png");
                break;
            case "Candidato C":
                imageIcon = new ImageIcon("image/candidateC.png");
                break;
        }
        // Redimensionar a imagem para que fique proporcional
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        candidateImageLabel.setIcon(new ImageIcon(scaledImage));
    }

    // Método para exibir os dados da blockchain
    private static void showBlockchainData() {
        BlockchainViewer.showBlockchainData(blockchain);
    }
}
