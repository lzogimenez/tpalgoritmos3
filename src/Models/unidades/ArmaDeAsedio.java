package Models.unidades;

import Models.escenario.Casillero;
import Models.escenario.errores.CasilleroAlejadoError;
import Models.escenario.errores.LugarOcupadoError;
import Models.juego.Jugador;
import Models.unidades.estadosArmaAsedio.ArmaMontadaNoPuedeMoverseError;
import Models.unidades.estadosUnidad.EstadoMontada;
import Models.unidades.estadosUnidad.EstadoNoMontada;

import java.util.ArrayList;

public class ArmaDeAsedio extends Unidad{

    public ArmaDeAsedio(){
        this.vida = 150;
        this.rangoAtaque = 5;
        this.danioAUnidad = 0;
        this.estadoAccion = new EstadoNoMontada();

    }
    public ArmaDeAsedio(Jugador jugador){
        this.propietario = jugador;
        this.vida = 150;
        this.estadoAccion =  new EstadoNoMontada();
        this.danioAEdificio = 75;
        this.danioAUnidad = 0; //No aplica, deberia lanzar excepcion cuando movamos el comportamiento aca
        ubicacion = new ArrayList<Casillero>();
    }

    public void montar(){
        this.estadoAccion = new EstadoMontada();
    }

    public void desmontar(){
        this.estadoAccion = new EstadoNoMontada();
    }

    @Override
    public void mover(Casillero nuevoCasillero)throws CasilleroAlejadoError, LugarOcupadoError {
        if (!estadoAccion.puedeMoverse()){
            throw new ArmaMontadaNoPuedeMoverseError();
        }
        if(this.ubicacion.get(0).distanciaHasta(nuevoCasillero) > 1)
            throw new CasilleroAlejadoError();
        if(nuevoCasillero.estaOcupado())
            throw new LugarOcupadoError();

        this.ubicacion.remove(this.ubicacion.get(0));

        nuevoCasillero.colocar(this);

        this.ubicacion.add( nuevoCasillero);

    }

}
