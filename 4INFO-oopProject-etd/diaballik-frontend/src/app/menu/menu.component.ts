import { Component, OnInit, AfterViewInit} from '@angular/core';
import {MyData} from '../mydata';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit, AfterViewInit {

  constructor(private http: HttpClient, private router: Router, private data: MyData) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  newPvPClicked(): void {
    this.http.post('/game/newPvP/Robin/0/Ronan/1', {}, {observe : 'response'}).
    subscribe(response => this.handleResponse(response));

    console.log('/game/newPvP/Robin/0/Ronan/1');
  }
  startClicked(): void {
    this.http.put('/game/start', {}, {observe : 'response' }).
    subscribe(response => this.handleResponse(response));

    console.log('/game/start');
  }

  endTurnClicked(): void {
    this.http.put('/game/action/endTurn', {}, {observe : 'response'}).
    subscribe(response => this.handleResponse(response));

    console.log('/game/endTurn');
  }

  undoClicked(): void {
    this.http.put('/game/action/undo', {}, {observe : 'response'}).
    subscribe(response => this.handleResponse(response));

    console.log('/game/undo');
  }

  redoClicked(): void {
    this.http.put('/game/action/redo', {}, {observe : 'response'}).
    subscribe(response => this.handleResponse(response));

    console.log('/game/redo');
  }

  onQuitClicked(): void {
    this.router.navigate(['gameSettings']);
  }

  handleResponse(response: HttpResponse<any>): void {
    if (response.status === 400) {
      console.log('Bad Request');
    } else {
      this.data.game = response.body;
    }
  }

}
