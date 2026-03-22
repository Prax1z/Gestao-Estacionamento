package model;

// Classe que representa um Carro no sistema de estacionamento.

public class Carro {

    private String marca;
    private String placa;
    private String cor;
    private int horaEntrada;
    private int horaSaida;

    //CONSTRUTORES


    public Carro() {
    }


    public Carro(String placa) {
        this.placa = placa;
    }


    public Carro(String marca, String placa, String cor) {
        this.marca = marca;
        this.placa = placa;
        this.cor = cor;
    }


    public Carro(String marca, String placa, String cor, int horaEntrada) {
        this.marca = marca;
        this.placa = placa;
        this.cor = cor;
        this.horaEntrada = horaEntrada;
    }


    public Carro(String marca, String placa, String cor, int horaEntrada, int horaSaida) {
        this.marca = marca;
        this.placa = placa;
        this.cor = cor;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    //GETTERS & SETTERS 

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(int horaSaida) {
        this.horaSaida = horaSaida;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "marca='" + marca + '\'' +
                ", placa='" + placa + '\'' +
                ", cor='" + cor + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", horaSaida=" + horaSaida +
                '}';
    }
}
