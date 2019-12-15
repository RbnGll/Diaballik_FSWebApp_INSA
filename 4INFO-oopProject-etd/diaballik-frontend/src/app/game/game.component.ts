import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {MyData} from "../mydata";
import {MenuComponent} from "../menu/menu.component";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  @ViewChild(MenuComponent, {static: false}) menuComponent: MenuComponent;

  constructor(private http: HttpClient, private router: Router, private data: MyData) { }

  ngOnInit() {

  }

}
