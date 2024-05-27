import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BlockchainViewer {

    public static void showBlockchainData(Blockchain blockchain) {
        // Criando uma nova tabela com os dados da blockchain
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Bloco");
        model.addColumn("Timestamp");
        model.addColumn("Voter ID");
        model.addColumn("Candidato");
        model.addColumn("Hash");
        model.addColumn("Previous Hash");
        model.addColumn("Nonce");
        for (Block block : blockchain.getBlocks()) {
            model.addRow(new Object[]{
                    block.getIndex(),
                    new java.util.Date(block.getTimestamp()).toString(),
                    block.getVoterId(),
                    block.getCandidate(),
                    block.getHash(),
                    block.getPreviousHash(),
                    block.getNonce()
            });
        }
        table.setModel(model);

        // Configurando a tabela dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        // Exibindo a caixa de diálogo com a tabela
        JOptionPane.showMessageDialog(null, scrollPane, "Dados da Blockchain", JOptionPane.PLAIN_MESSAGE);
    }
}
