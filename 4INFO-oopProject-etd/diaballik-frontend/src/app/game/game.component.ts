import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {MyData} from "../mydata";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private data: MyData) {
    // Démarrer la game
    this.http.put(`/game/start`, {}, {}).
    subscribe(returnedData => this.data.game = returnedData);

    console.log('/game/start');
  }

  ngOnInit() {

  }

  /**
   * Fonction appellé lorsque le board relève une victoire d'un joueur
   * @param event.player = 1 ou 2 suivant le joueur gagnant
   */
  onVictory(event: any) {
    console.log(`Victory Player ${event.player}`);
  }

}
