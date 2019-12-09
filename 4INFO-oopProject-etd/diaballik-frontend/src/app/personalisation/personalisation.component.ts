import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-personalisation',
  templateUrl: './personalisation.component.html',
  styleUrls: ['./personalisation.component.css']
})

export class PersonalisationComponent implements OnInit, AfterViewInit {

  playersData = this.fb.group({
    player1: this.fb.group({
      player1Name: [''],
      player1Type: [''],
      player1AILevel: ['']
    })
  });

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) { }

  ngAfterViewInit(): void {

  }

  ngOnInit() {
    this.playersData.get('player1').get('player1Type').setValue('Joueur');
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
