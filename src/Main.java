public class Main {
    public static void main(String[] args) {
        //long inicio = System.nanoTime(); // Marca o tempo inicial

        Grafo grafo = new Grafo(10, 5); // Criação do grafo

        //long fim = System.nanoTime(); // Marca o tempo final
        //long tempoTotal = fim - inicio; // Calcula o tempo decorrido

        //System.out.println("Tempo para criar o grafo: " + (tempoTotal / 1_000) + " µs");

        grafo.imprimirGrafo();
        //grafo.getVertice(4);


        grafo.buscaProfundidade(1, 4);
        //grafo.buscaLargura(1, 4);
        //grafo.buscaLimitada(1, 4, 3);


    }

}
