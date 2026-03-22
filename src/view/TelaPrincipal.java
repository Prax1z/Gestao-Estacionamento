package view;

import dao.CarroDAO;
import model.Carro;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

// Tela principal da aplicação de controle de estacionamento.

public class TelaPrincipal extends JFrame {

    private JTextField txtMarca, txtPlaca, txtCor, txtHoraEntrada, txtHoraSaida;
    private JButton btnIncluir, btnAlterar, btnExcluir, btnLimpar, btnBuscar;
    private JTable tabelaCarros;
    private DefaultTableModel modeloTabela;
    private CarroDAO carroDAO;
    private JTabbedPane abas;

    public TelaPrincipal() {
        carroDAO = new CarroDAO();
        initComponents();
        configurarJanela();
    }

    // Inicialização

    private void initComponents() {
        abas = new JTabbedPane();
        abas.addTab("Cadastro", criarPainelCadastro());
        abas.addTab("Relatório", criarPainelRelatorio());

        setLayout(new BorderLayout());
        add(abas, BorderLayout.CENTER);
    }

    // Aba Cadastro

    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Formulário
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Dados do Veículo"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Placa + Marca
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        form.add(new JLabel("Placa:"), gbc);
        txtPlaca = new JTextField(10);
        gbc.gridx = 1; gbc.weightx = 0.3;
        form.add(txtPlaca, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("Marca:"), gbc);
        txtMarca = new JTextField(20);
        gbc.gridx = 3; gbc.weightx = 0.7;
        form.add(txtMarca, gbc);

        // Cor
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Cor:"), gbc);
        txtCor = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        form.add(txtCor, gbc);
        gbc.gridwidth = 1;

        // Horas
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        form.add(new JLabel("Hora Entrada (0-23):"), gbc);
        txtHoraEntrada = new JTextField(5);
        gbc.gridx = 1; gbc.weightx = 0.3;
        form.add(txtHoraEntrada, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("Hora Saída (0-23):"), gbc);
        txtHoraSaida = new JTextField(5);
        gbc.gridx = 3; gbc.weightx = 0.3;
        form.add(txtHoraSaida, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnBuscar  = new JButton("Buscar");
        btnIncluir = new JButton("Incluir");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar  = new JButton("Limpar");

        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnIncluir);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        painel.add(form,         BorderLayout.NORTH);
        painel.add(painelBotoes, BorderLayout.CENTER);

        configurarEventos();
        return painel;
    }

    // Aba Relatório

    private JPanel criarPainelRelatorio() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        String[] colunas = {"Placa", "Marca", "Cor", "Hora Entrada", "Hora Saída"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaCarros = new JTable(modeloTabela);
        tabelaCarros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCarros.getTableHeader().setReorderingAllowed(false);
        tabelaCarros.setRowHeight(22);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarTabela());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topo.add(btnAtualizar);

        painel.add(topo,                          BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaCarros), BorderLayout.CENTER);

        carregarTabela();

        // Clique na linha da tabela → carrega formulário
        tabelaCarros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaCarros.getSelectedRow();
                if (linha >= 0) {
                    txtPlaca      .setText((String) modeloTabela.getValueAt(linha, 0));
                    txtMarca      .setText((String) modeloTabela.getValueAt(linha, 1));
                    txtCor        .setText((String) modeloTabela.getValueAt(linha, 2));
                    txtHoraEntrada.setText(String.valueOf(modeloTabela.getValueAt(linha, 3)));
                    txtHoraSaida  .setText(String.valueOf(modeloTabela.getValueAt(linha, 4)));
                    abas.setSelectedIndex(0);
                }
            }
        });

        return painel;
    }

    // Eventos

    private void configurarEventos() {

        btnBuscar.addActionListener(e -> {
            String placa = txtPlaca.getText().trim().toUpperCase();
            if (placa.isEmpty()) { aviso("Digite a placa para buscar."); return; }
            Carro c = carroDAO.buscarPorPlaca(placa);
            if (c != null) preencherFormulario(c);
            else           aviso("Veículo com placa " + placa + " não encontrado.");
        });

        btnIncluir.addActionListener(e -> {
            Carro c = obterCarroDoFormulario();
            if (c == null) return;
            if (carroDAO.incluir(c)) { info("Veículo incluído com sucesso!"); limpar(); carregarTabela(); }
            else                      erro("Erro ao incluir. Verifique se a placa já existe.");
        });

        btnAlterar.addActionListener(e -> {
            Carro c = obterCarroDoFormulario();
            if (c == null) return;
            if (carroDAO.alterar(c)) { info("Veículo alterado com sucesso!"); carregarTabela(); }
            else                      erro("Erro ao alterar. Veículo não encontrado.");
        });

        btnExcluir.addActionListener(e -> {
            String placa = txtPlaca.getText().trim().toUpperCase();
            if (placa.isEmpty()) { aviso("Digite a placa para excluir."); return; }
            int op = JOptionPane.showConfirmDialog(this,
                    "Excluir o veículo de placa " + placa + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                if (carroDAO.excluir(placa)) { info("Veículo excluído!"); limpar(); carregarTabela(); }
                else                          erro("Erro ao excluir. Veículo não encontrado.");
            }
        });

        btnLimpar.addActionListener(e -> limpar());
    }

    // Auxiliares

    private Carro obterCarroDoFormulario() {
        String placa  = txtPlaca.getText().trim().toUpperCase();
        String marca  = txtMarca.getText().trim();
        String cor    = txtCor.getText().trim();
        String entStr = txtHoraEntrada.getText().trim();
        String saiStr = txtHoraSaida.getText().trim();

        if (placa.isEmpty() || marca.isEmpty() || cor.isEmpty()
                || entStr.isEmpty() || saiStr.isEmpty()) {
            aviso("Preencha todos os campos.");
            return null;
        }
        try {
            int hEnt = Integer.parseInt(entStr);
            int hSai = Integer.parseInt(saiStr);
            if (hEnt < 0 || hEnt > 23 || hSai < 0 || hSai > 23) {
                aviso("Hora deve ser um valor entre 0 e 23.");
                return null;
            }
            return new Carro(marca, placa, cor, hEnt, hSai);
        } catch (NumberFormatException ex) {
            aviso("Hora de entrada e saída devem ser números inteiros.");
            return null;
        }
    }

    private void preencherFormulario(Carro c) {
        txtPlaca      .setText(c.getPlaca());
        txtMarca      .setText(c.getMarca());
        txtCor        .setText(c.getCor());
        txtHoraEntrada.setText(String.valueOf(c.getHoraEntrada()));
        txtHoraSaida  .setText(String.valueOf(c.getHoraSaida()));
    }

    private void limpar() {
        txtPlaca.setText(""); txtMarca.setText(""); txtCor.setText("");
        txtHoraEntrada.setText(""); txtHoraSaida.setText("");
        txtPlaca.requestFocus();
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Carro c : carroDAO.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                c.getPlaca(), c.getMarca(), c.getCor(),
                c.getHoraEntrada(), c.getHoraSaida()
            });
        }
    }

    private void info (String msg) { JOptionPane.showMessageDialog(this, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE); }
    private void aviso(String msg) { JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);     }
    private void erro (String msg) { JOptionPane.showMessageDialog(this, msg, "Erro",    JOptionPane.ERROR_MESSAGE);       }

    // Janela

    private void configurarJanela() {
        setTitle("Sistema de Estacionamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);
        setMinimumSize(new Dimension(560, 340));
        setLocationRelativeTo(null);
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
