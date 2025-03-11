import java.util.*;

public class Grafo {
    private int qtdVertices;
    private int qtdArestas;
    private Map<Integer, Set<Integer>> listaAdj;
    private int[][] matrizAdj;

    // Classe interna criada para armazenar o nó e sua profundidade na busca limitada
    private static class Par {
        int no;
        int profundidade;

        public Par(int no, int profundidade) {
            this.no = no;
            this.profundidade = profundidade;
        }
    }

    //Construtor do grafo
    public Grafo(int qtdVertices, int qtdArestas) throws Exception{
        if (qtdArestas > qtdVertices - 1) {
            throw new Exception("Impossível criar o grafo. Cada vértice pode ter no máximo " + (qtdVertices - 1) + " arestas.");
        }
        this.qtdVertices = qtdVertices;
        this.qtdArestas = qtdArestas;
        this.listaAdj = new HashMap<>();
        if (grafoCompleto(qtdVertices, qtdArestas))
            this.matrizAdj = new int[qtdVertices][qtdVertices];

        System.out.println("\nConstruindo o Grafo:");
        System.out.println("-> Criando Vértices...");
        for (int i = 0; i < qtdVertices; i++) {
            listaAdj.put(i, new HashSet<>()); // Inicializa a lista de vizinhos
        }

        System.out.println("-> Criando Arestas...");
        criarArestas();

        System.out.println("--> Grafo Criado com sucesso! " +
                "\n   -> Quantidade de vértices: " + qtdVertices +
                "\n   -> Quantidade de arestas por vértice: " + qtdArestas);
    }

    private void criarArestas() throws Exception {

        if (grafoCompleto(qtdVertices, qtdArestas)) {
            System.out.println("Grafo completo, será usado matriz.");
            for (int i = 0; i < qtdVertices; i++) {
                for (int j = 0; j < qtdVertices; j++) {
                    if (i != j) {
                        matrizAdj[i][j] = 1;
                    }
                }
            }
            System.out.println("Grafo completo construido");
        } else {
            Random random = new Random();
            int maximoTentativas = qtdVertices * qtdArestas * 2;
            for (int v = 0; v < qtdVertices; v++) {
                int tentativas = 0;

                while (listaAdj.get(v).size() < qtdArestas && tentativas < maximoTentativas) {
                    tentativas++;
                    int novoVizinho = random.nextInt(qtdVertices);

                    // Evita laços, arestas duplicadas e garante que o vizinho ainda não atingiu o limite
                    if (novoVizinho != v && !listaAdj.get(v).contains(novoVizinho) && listaAdj.get(novoVizinho).size() < qtdArestas) {
                        listaAdj.get(v).add(novoVizinho);
                        listaAdj.get(novoVizinho).add(v); // Grafo não-direcionado
                    }
                }
                if (listaAdj.get(v).size() < qtdArestas) {
                    throw new Exception("Não foi possível adicionar todas as arestas para o vértice " + v +
                            " após " + maximoTentativas + " tentativas.");
                }
            }
        }




    }

    public void imprimirGrafo() {
        System.out.println("Lista de Adjacência:");
        for (int v : listaAdj.keySet()) {
            System.out.println(v + " -> " + listaAdj.get(v));
        }
    }

    public boolean grafoCompleto (int qtdVertices, int qtdArestas) {
        return qtdArestas == (qtdVertices - 1);
    }

    public void buscaProfundidade(int origem, int destino) {
        Stack<Integer> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        pilha.push(origem);

        while (!pilha.isEmpty()) {
            int atual = pilha.pop();

            if (visitados.contains(atual)) continue;

            visitados.add(atual);
            interacoes++;

            if (atual == destino) {
                System.out.println("Destino encontrado em " + interacoes + " interações.");
                return;
            }

            if (grafoCompleto(qtdVertices, qtdArestas)) {
                for (int vizinho = 0; vizinho < qtdVertices; vizinho++) {
                    if (matrizAdj[atual][vizinho] == 1 && !visitados.contains(vizinho)) {
                        pilha.push(vizinho);
                    }
                }
            } else {
                for (int vizinho : listaAdj.get(atual)) {
                    if (!visitados.contains(vizinho)) {
                        pilha.push(vizinho);
                    }
                }
            }
        }

        System.out.println("Destino não encontrado.");
    }

    public void buscaProfundidadeLimitada(int origem, int destino, int limite) {
        Stack<Par> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        pilha.push(new Par(origem, 0));

        while (!pilha.isEmpty()) {
            Par atual = pilha.pop();

            if (visitados.contains(atual.no)) {
                continue;
            }

            visitados.add(atual.no);
            interacoes++;

            if (atual.no == destino) {
                System.out.println("Destino encontrado em " + interacoes + " interações.");
                return;
            }

            if (atual.profundidade < limite) {
                if (grafoCompleto(qtdVertices, qtdArestas)) {
                    for (int vizinho = 0; vizinho < qtdVertices; vizinho++) {
                        if (matrizAdj[atual.no][vizinho] == 1 && !visitados.contains(vizinho)) {
                            pilha.push(new Par(vizinho, atual.profundidade + 1));
                        }
                    }
                } else {
                    for (int vizinho : listaAdj.get(atual.no)) {
                        if (!visitados.contains(vizinho)) {
                            pilha.push(new Par(vizinho, atual.profundidade + 1));
                        }
                    }
                }
            }
        }

        System.out.println("Destino não encontrado dentro do limite de profundidade " + limite + ".");
    }

    public void buscaLargura(int origem, int destino) {
        Queue<Integer> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            int atual = fila.poll();
            interacoes++;

            if (atual == destino) {
                System.out.println("Destino encontrado em " + interacoes + " interações.");
                return;
            }

            if (grafoCompleto(qtdVertices, qtdArestas)) {
                for (int vizinho = 0; vizinho < qtdVertices; vizinho++) {
                    if (matrizAdj[atual][vizinho] == 1 && !visitados.contains(vizinho)) {
                        fila.add(vizinho);
                        visitados.add(vizinho);
                    }
                }
            } else {
                for (int vizinho : listaAdj.get(atual)) {
                    if (!visitados.contains(vizinho)) {
                        fila.add(vizinho);
                        visitados.add(vizinho);
                    }
                }
            }
        }

        System.out.println("Destino não encontrado.");
    }
}
