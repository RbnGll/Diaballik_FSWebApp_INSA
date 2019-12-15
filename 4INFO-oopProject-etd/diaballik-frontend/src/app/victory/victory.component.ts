import { Component, OnInit } from '@angular/core';
import {MyData} from "../mydata";
import {Router} from "@angular/router";

@Component({
  selector: 'app-victory',
  templateUrl: './victory.component.html',
  styleUrls: ['./victory.component.css']
})
export class VictoryComponent implements OnInit {

  message: string;

  constructor(private data: MyData, private router: Router) {
    // Création du message

    // Si le jeu comporte une IA
    if (this.data.game.player2.type === 'aiPlayer') {
      if (this.data.game.player2.victory === true) {
        this.message = 'Vous avez perdu...';
      }
      else {
        this.message = 'Bravo !';
      }
    }
    // Si il s'agit de 2 joueurs
    else {
      // Si c'est le joueur 1 qui a gagné
      if (this.data.game.player1.victory === true) {
        this.message = `Victoire de ${this.data.game.player1.name}`;
      }
      else {
        this.message = `Victoire de ${this.data.game.player2.name}`;
      }
    }
  }

  ngOnInit() {
  }

}
