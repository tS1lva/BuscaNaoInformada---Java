import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int qtdAresta;
    private ArrayList<Aresta> arestas;
    private int valor;

    /*
    public Vertice ( int valor, int qtdAresta, List<Integer> verticeDestino) {
        this.arestas = new ArrayList<Aresta>();
        this.qtdAresta = qtdAresta;
        this.valor = valor;

        for (int i=0 ; i< qtdAresta ; i++) {
            int j = verticeDestino.get(i);
            this.arestas.add(new Aresta(j));
        }
    }*/

    public Vertice(int valor) {
        this.arestas = new ArrayList<>();
        this.valor = valor;
    }

    // GETTERS
    public int getValor(){
        return this.valor;
    }

    public int getQtdAresta(){
        return this.qtdAresta;
    }

    public ArrayList<Aresta> getArestas() {
        return this.arestas;
    }


    // SETTERS
    public void setValor(int valor) {
        this.valor = valor;
    }

    // Metodos padrao
    public String toString() {
        String s = "Vertice " + this.getValor() + " -> ";

        ArrayList<Aresta> arestas = this.getArestas();
        if (arestas.isEmpty()) {
            s += "Sem arestas";
        } else {
            for (Aresta aresta : arestas) {
                s += aresta.getDestino();
                s += " ";
            }
        }
        return s;
    }



}
