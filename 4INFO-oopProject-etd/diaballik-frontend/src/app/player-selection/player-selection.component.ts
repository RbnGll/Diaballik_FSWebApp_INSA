import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-player-selection',
  templateUrl: './player-selection.component.html',
  styleUrls: ['./player-selection.component.css']
})
export class PlayerSelectionComponent implements OnInit {

  private playerType: string;
  private playerName: string;
  // TODO : Change the type
  private playerColor: any;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.playerType = 'Joueur';
  }

}
