import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from "@angular/router";
import {customAnimation} from "./animation";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    customAnimation
  ]
})

export class AppComponent {
  title = 'Diaballik';

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }
}
