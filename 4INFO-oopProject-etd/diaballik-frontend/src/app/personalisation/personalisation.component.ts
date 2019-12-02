import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-personalisation',
  templateUrl: './personalisation.component.html',
  styleUrls: ['./personalisation.component.css']
})

export class PersonalisationComponent implements OnInit {

  playersData = this.fb.group({
    player1: this.fb.group({
      player1Name: [''],
      player1Type: ['', Validators.required],
      player1AILevel: [''],
      player1Color: ['', Validators.required]
    }),
    player2: this.fb.group({
      player2Name: [''],
      player2Type: ['', Validators.required],
      player2AILevel: [''],
      player2Color: ['', Validators.required]
    })
  })

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) { }

  ngOnInit() {
    this.playersData.get('player1').get('player1Type').setValue('Joueur');
    this.playersData.get('player2').get('player2Type').setValue('Joueur');

    this.playersData.get('player1').get('player1Color').setValue('Noir');
    this.playersData.get('player2').get('player2Color').setValue('Blanc');

    this.playersData.get('player1').get('player1Type').valueChanges.subscribe(type => {
      if(type === 'Joueur') {
        // Enlever les validateurs sur le champ Level
        this.playersData.get('player1').get('player1AILevel').clearValidators();
        // Les remettre sur le champ Name
        this.playersData.get('player1').get('player1Name').setValidators([Validators.required]);
      }

      else {
        this.playersData.get('player1').get('player1AILevel').setValidators(Validators.required);
        this.playersData.get('player1').get('player1Name').clearValidators();
      }
    })

    this.playersData.get('player1').get('player1Type').valueChanges.subscribe(type => {
      if(type === 'Joueur') {
        // Enlever les validateurs sur le champ Level
        this.playersData.get('player2').get('player2AILevel').clearValidators();
        // Les remettre sur le champ Name
        this.playersData.get('player2').get('player2Name').setValidators([Validators.required]);
      }

      else {
        this.playersData.get('player2').get('player2AILevel').setValidators(Validators.required);
        this.playersData.get('player2').get('player2Name').clearValidators();
      }
    })
  }

  onSubmit(): void {
    // TODO : Crée un game avec les paramètres renseignés dans les select
    console.log(this.playersData.value);

    // Send request

    // Navigate to the created game
    //this.router.navigate(['game']);
  }

  onQuitClicked(): void {
    this.router.navigate(['']);
  }

}
