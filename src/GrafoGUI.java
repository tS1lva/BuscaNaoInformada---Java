import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class GrafoGUI extends JFrame {
    private Grafo grafo;
    private JPanel painelGrafo;
    private JTextArea resultados;
    private JComboBox<String> algoritmos;
    private JButton executarBusca;
    private int origem = 0;
    private int destino;

    public GrafoGUI() {
        setTitle("Visualização de Algoritmos de Busca em Grafos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Criar painel onde o grafo será desenhado
        painelGrafo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharGrafo(g);
            }
        };
        painelGrafo.setPreferredSize(new Dimension(500, 500));

        // Criar área de resultados
        resultados = new JTextArea(10, 30);
        resultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(resultados);

        // Criar combobox para seleção de algoritmo
        algoritmos = new JComboBox<>(new String[]{"Busca em Largura (BFS)", "Busca em Profundidade (DFS)"});
        executarBusca = new JButton("Executar Busca");

        executarBusca.addActionListener(e -> executarBusca());

        // Criar painel lateral com controles
        JPanel painelControles = new JPanel();
        painelControles.setLayout(new BoxLayout(painelControles, BoxLayout.Y_AXIS));
        painelControles.add(new JLabel("Escolha o Algoritmo:"));
        painelControles.add(algoritmos);
        painelControles.add(executarBusca);
        painelControles.add(scrollResultados);

        // Adicionar os componentes à interface
        add(painelGrafo, BorderLayout.CENTER);
        add(painelControles, BorderLayout.EAST);

        gerarGrafo();
    }

    private void gerarGrafo() {
        int qtdVertices = 10;
        int qtdArestas = 3; // Média de 3 arestas por vértice
        grafo = new Grafo(qtdVertices, qtdArestas);
        destino = qtdVertices - 1; // Último vértice como destino
        repaint();
    }

    private void desenharGrafo(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Map<Integer, Point> posicoes = new HashMap<>();
        Random random = new Random();

        // Gerar posições aleatórias para os vértices
        for (Vertice v : grafo.getVertices()) {
            int x = random.nextInt(painelGrafo.getWidth() - 50) + 25;
            int y = random.nextInt(painelGrafo.getHeight() - 50) + 25;
            posicoes.put(v.getValor(), new Point(x, y));
        }

        // Desenhar arestas
        g2.setColor(Color.GRAY);
        for (Vertice v : grafo.getVertices()) {
            Point p1 = posicoes.get(v.getValor());
            for (Aresta a : v.getArestas()) {
                Point p2 = posicoes.get(a.getDestino());
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Desenhar vértices
        for (Vertice v : grafo.getVertices()) {
            Point p = posicoes.get(v.getValor());
            g2.setColor(v.getValor() == origem ? Color.GREEN : v.getValor() == destino ? Color.RED : Color.BLUE);
            g2.fill(new Ellipse2D.Double(p.x - 10, p.y - 10, 20, 20));
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(v.getValor()), p.x - 5, p.y + 5);
        }
    }

    private void executarBusca() {
        String algoritmoSelecionado = (String) algoritmos.getSelectedItem();
        long tempoInicio = System.nanoTime();
        List<Integer> caminho = new ArrayList<>();

        if ("Busca em Largura (BFS)".equals(algoritmoSelecionado)) {
            caminho = buscaLargura(origem, destino);
        } else if ("Busca em Profundidade (DFS)".equals(algoritmoSelecionado)) {
            caminho = buscaProfundidade(origem, destino);
        }

        long tempoFim = System.nanoTime();
        double tempoExecucao = (tempoFim - tempoInicio) / 1e6; // Convertendo para milissegundos

        // Exibir os resultados
        resultados.setText("Algoritmo: " + algoritmoSelecionado + "\n");
        resultados.append("Caminho encontrado: " + caminho + "\n");
        resultados.append("Tamanho do caminho: " + caminho.size() + "\n");
        resultados.append("Tempo de execução: " + tempoExecucao + " ms\n");

        repaint();
    }

    private List<Integer> buscaLargura(int origem, int destino) {
        Queue<Vertice> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> caminho = new HashMap<>();
        List<Integer> caminhoFinal = new ArrayList<>();

        fila.add(grafo.getVertice(origem));
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            if (atual.getValor() == destino) break;

            for (Aresta aresta : atual.getArestas()) {
                int vizinho = aresta.getDestino();
                if (!visitados.contains(vizinho)) {
                    fila.add(grafo.getVertice(vizinho));
                    visitados.add(vizinho);
                    caminho.put(vizinho, atual.getValor());
                }
            }
        }

        Integer passo = destino;
        while (passo != null) {
            caminhoFinal.add(0, passo);
            passo = caminho.get(passo);
        }

        return caminhoFinal;
    }

    private List<Integer> buscaProfundidade(int origem, int destino) {
        Stack<Vertice> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        List<Integer> caminho = new ArrayList<>();

        pilha.push(grafo.getVertice(origem));

        while (!pilha.isEmpty()) {
            Vertice atual = pilha.pop();
            if (visitados.contains(atual.getValor())) continue;

            caminho.add(atual.getValor());
            visitados.add(atual.getValor());

            if (atual.getValor() == destino) break;

            for (Aresta a : atual.getArestas()) {
                if (!visitados.contains(a.getDestino())) {
                    pilha.push(grafo.getVertice(a.getDestino()));
                }
            }
        }

        return caminho;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GrafoGUI().setVisible(true));
    }
}
