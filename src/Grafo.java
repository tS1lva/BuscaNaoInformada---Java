import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class Grafo {
    private int qtdVertices;
    private int qtdArestas;
    private ArrayList<Vertice> vertices;

    public Grafo(int qtdVertices, int qtdArestas) {
        this.qtdVertices = qtdVertices;
        this.qtdArestas = qtdArestas;
        this.vertices = new ArrayList<>();

        // Criar vértices
        for (int i = 0; i < qtdVertices; i++) {
            this.vertices.add(new Vertice(i));
        }

        // Criar arestas garantindo todas as condições
        Random random = new Random();
        Set<String> arestasCriadas = new HashSet<>();

        while (arestasCriadas.size() / 2 < qtdVertices * qtdArestas / 2) {
            int v1 = random.nextInt(qtdVertices);
            int v2 = random.nextInt(qtdVertices);

            // Garantir que não aponte para si mesmo e que a aresta não foi criada antes
            if (v1 != v2 && !arestasCriadas.contains(v1 + "-" + v2)) {
                // Verificar se ainda pode adicionar arestas nos dois vértices
                if (vertices.get(v1).getArestas().size() < qtdArestas &&
                        vertices.get(v2).getArestas().size() < qtdArestas) {

                    // Criar a aresta nos dois sentidos
                    vertices.get(v1).getArestas().add(new Aresta(v2));
                    vertices.get(v2).getArestas().add(new Aresta(v1));

                    // Marcar a aresta como criada nos dois sentidos
                    arestasCriadas.add(v1 + "-" + v2);
                    arestasCriadas.add(v2 + "-" + v1);
                }
            }
        }
    }



    private int getRandomDestiny(int atual, int qtdArestas) {
        Random random = new Random();
        int destino;
        do {
            destino = random.nextInt(0, qtdArestas);
        } while (destino == atual);

        return destino;
    }

    private List<Integer> getRandomEdgeList(int origin, int bound, int qtdArestas, int verticeAtual) {
        List<Integer> listaArestas = new ArrayList<>(List.of());
        Random random = new Random();

        for (int i=0 ; i<= qtdArestas ; i++) {

            do {
                int aleatorio = random.nextInt(origin, bound);
                if ((aleatorio != verticeAtual) && (!listaArestas.contains(aleatorio))){
                    listaArestas.add(aleatorio);
                }
            } while (listaArestas.size() <= i);

        }
        return listaArestas;
    }

    public void imprimirGrafo() {
        for (Vertice vertice : vertices) {
            System.out.print("Vértice " + vertice.getValor() + " -> ");
            for (Aresta aresta : vertice.getArestas()) {
                System.out.print(aresta.getDestino() + " ");
            }
            System.out.println();
        }
    }


    public void buscaProfundidade(int origem, int destino) {
        Stack<Vertice> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        Vertice verticeInicial = getVertice(origem);

        if (verticeInicial == null ) {
            System.out.println("Vertice inicial não encontrado!");
        }

        pilha.push(verticeInicial);

        while(!pilha.isEmpty()) {
            Vertice verticeAtual = pilha.pop();

            if (visitados.contains(verticeAtual.getValor())) {
                continue;
            }

            System.out.println("Visitando o vertice " + verticeAtual.getValor());
            visitados.add(verticeAtual.getValor());
            interacoes ++;

            if (verticeAtual.getValor() == destino){
                System.out.println("Encontrado o valor buscado! " + destino);
                System.out.println("Total de interações: " + interacoes);
                return;
            }

            for (Aresta a: verticeAtual.getArestas()) {
                Vertice vizinho = getVertice(a.getDestino());

                if (vizinho != null && !visitados.contains(vizinho.getValor())){
                    pilha.push(vizinho);
                }
            }
        }

        System.out.println("Vertice de destino Não encontrado!");
        System.out.println("Total de interações: " + interacoes);

    }


    public void buscaLargura(int origem, int destino) {
        Queue<Vertice> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        int interacoes = 0;

        Vertice verticeInicial = getVertice(origem);

        if (verticeInicial == null) {
            System.out.println("Vértice inicial não encontrado!");
            return;
        }

        fila.add(verticeInicial);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Vertice verticeAtual = fila.poll();

            System.out.println("Visitando o vértice: " + verticeAtual.getValor());
            interacoes ++;

            if (verticeAtual.getValor() == destino) {
                System.out.println("Encontrado o valor buscado! " + destino);
                System.out.println("Total de interacoes: " + interacoes);
                return;
            }

            for (Aresta a : verticeAtual.getArestas()) {
                Vertice vizinho = getVertice(a.getDestino());

                if (vizinho != null && !visitados.contains(vizinho.getValor())) {
                    fila.add(vizinho);
                    visitados.add(vizinho.getValor());
                }
            }
        }

        System.out.println("Vértice de destino não encontrado!");
        System.out.println("Total de interacoes: " + interacoes);
    }

    public boolean buscaLimitada(int origem, int destino, int limite) {
        Set<Integer> visitados = new HashSet<>();
        AtomicInteger iteracoes = new AtomicInteger(0);

        boolean encontrado = buscaLimitadaRecursiva(getVertice(origem), destino, limite, visitados, 0, iteracoes);

        System.out.println("Total de iterações: " + iteracoes.get());
        return encontrado;
    }

    private boolean buscaLimitadaRecursiva(Vertice atual, int destino, int limite, Set<Integer> visitados, int profundidade, AtomicInteger iteracoes) {
        if (atual == null) {
            return false;
        }

        iteracoes.incrementAndGet();
        System.out.println("Visitando o vértice: " + atual.getValor() + " (Profundidade: " + profundidade + ")");

        if (atual.getValor() == destino) {
            System.out.println("Encontrado o destino: " + destino);
            return true;
        }

        if (profundidade >= limite) {
            System.out.println("Limite de profundidade atingido no vértice " + atual.getValor());
            return false;
        }

        visitados.add(atual.getValor());

        for (Aresta a : atual.getArestas()) {
            Vertice vizinho = getVertice(a.getDestino());

            if (vizinho != null && !visitados.contains(vizinho.getValor())) {
                if (buscaLimitadaRecursiva(vizinho, destino, limite, visitados, profundidade + 1, iteracoes)) {
                    return true;
                }
            }
        }

        return false;
    }


    public Vertice getVertice(int valor) {
        for (Vertice vertice : vertices) {
            if (vertice.getValor() == valor) {
                return vertice;
            }
        }
        return null;
    }

    public ArrayList<Vertice> getVertices() {
        return this.vertices;
    }

}
