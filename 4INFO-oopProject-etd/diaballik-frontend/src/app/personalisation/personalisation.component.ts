import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { FormBuilder } from '@angular/forms';
import {MyData} from '../mydata';

@Component({
  selector: 'app-personalisation',
  templateUrl: './personalisation.component.html',
  styleUrls: ['./personalisation.component.css']
})

export class PersonalisationComponent implements OnInit {

  playersData = this.fb.group({
    player1: this.fb.group({
      player1Name: [''],
      player1AILevel: [''],
      player1Color: ['']
    }),
    player2 : this.fb.group(({
      player2Name: [''],
      player2Type: [''],
      player2AILevel: [''],
    }))
  });

  // Champ à part
  player2Color: string;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router, private data: MyData) {
    this.player2Color = 'Blanc';
  }

  ngOnInit() {
    this.playersData.get('player1').get('player1Color').setValue('Noir');
    this.playersData.get('player2').get('player2Type').setValue('Joueur');

    this.playersData.get('player1').get('player1Color').valueChanges.subscribe(value => {
      if (value === 'Noir') {
        this.player2Color = 'Blanc';
      } else {
        this.player2Color = 'Noir';
      }
    });
  }

  onSubmit(): void {
    // console.log(this.playersData.value);

    // Choisir le type de requête à envoyer
    if (this.playersData.value.player2.player2Type === 'IA') {
      // Nouvelle partie Joueur VS AI
      const name: string = this.playersData.value.player1.player1Name;
      const color: number = this.playersData.value.player1.player1Color === 'Noir' ? 0 : 1;

      // Déterminer la stratégie
      let strategy: number;
      switch (this.playersData.value.player2.player2AILevel) {
        case 'Noob':
          strategy = 0;
          break;
        case 'Starting':
          strategy = 1;
          break;
        case 'Progressive':
          strategy = 3;
          break;
      }

      // Envoi de la requête
      this.http.post(`/game/newPvAI/${name}/${color}/${strategy}`, {}, {}).
        subscribe(returnedData => this.data.game = returnedData);

      console.log(`/game/newPvAI/${name}/${color}/${strategy}`);

    } else {
      // Nouvelle partie Joueur VS Joueur
      const name1: string = this.playersData.value.player1.player1Name;
      const color1: number = this.playersData.value.player1.player1Color === 'Noir' ? 0 : 1;
      let name2: string = this.playersData.value.player2.player2Name;
      const color2: number = 1 - color1;

      if (name1 === name2) {
        name2 += ' (2)';
      }

      this.http.post(`/game/newPvP/${name1}/${color1}/${name2}/${color2}`, {}, {}).
        subscribe(returnedData => this.data.game = returnedData);

      console.log(`/game/newPvP/${name1}/${color1}/${name2}/${color2}`);
    }

    // Démarrer la game
    this.http.put(`/game/start`, {}, {}).
    subscribe(returnedData => this.data.game = returnedData);

    console.log('/game/start');

    // Navigate to the created game
    this.router.navigate(['game']);
  }

  onQuitClicked(): void {
    this.router.navigate(['']);
  }

}
