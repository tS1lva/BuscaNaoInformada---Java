import java.util.*;

public class Grafo {
    private int qtdVertices;
    private int qtdArestas;
    private Map<Integer, Set<Integer>> adj; // Lista de adjacência usando um Map

    public Grafo(int qtdVertices, int qtdArestas) {
        this.qtdVertices = qtdVertices;
        this.qtdArestas = qtdArestas;
        this.adj = new HashMap<>();

        for (int i = 0; i < qtdVertices; i++) {
            adj.put(i, new HashSet<>()); // Inicializa a lista de vizinhos
        }

        criarArestas();
    }

    private void criarArestas() {
        Random random = new Random();

        for (int v = 0; v < qtdVertices; v++) {
            while (adj.get(v).size() < qtdArestas) {
                int novoVizinho = random.nextInt(qtdVertices);

                // Evita laços e arestas duplicadas
                if (novoVizinho != v && adj.get(v).size() < qtdArestas && adj.get(novoVizinho).size() < qtdArestas) {
                    adj.get(v).add(novoVizinho);
                    adj.get(novoVizinho).add(v); // Grafo não-direcionado
                }
            }
        }
    }

    public void imprimirGrafo() {
        System.out.println("Lista de Adjacência:");
        for (int v : adj.keySet()) {
            System.out.println(v + " -> " + adj.get(v));
        }
    }

    public void buscaProfundidade(int origem, int destino) {
        Stack<Integer> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        pilha.push(origem);

        while (!pilha.isEmpty()) {
            int atual = pilha.pop();

            if (visitados.contains(atual)) continue;

            //System.out.println("Visitando: " + atual);
            visitados.add(atual);
            interacoes++;

            if (atual == destino) {
                System.out.println("Destino encontrado em " + interacoes + " interações.");
                return;
            }

            for (int vizinho : adj.get(atual)) {
                if (!visitados.contains(vizinho)) {
                    pilha.push(vizinho);
                }
            }
        }

        System.out.println("Destino não encontrado.");
    }

    public void buscaLargura(int origem, int destino) {
        Queue<Integer> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            int atual = fila.poll();
            //System.out.println("Visitando: " + atual);
            interacoes++;

            if (atual == destino) {
                System.out.println("Destino encontrado em " + interacoes + " interações.");
                return;
            }

            for (int vizinho : adj.get(atual)) {
                if (!visitados.contains(vizinho)) {
                    fila.add(vizinho);
                    visitados.add(vizinho);
                }
            }
        }

        System.out.println("Destino não encontrado.");
    }
}
