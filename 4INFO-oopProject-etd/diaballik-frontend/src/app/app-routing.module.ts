import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {PersonalisationComponent} from './personalisation/personalisation.component';
import {GameComponent} from './game/game.component';
import {VictoryComponent} from './victory/victory.component';


const routes: Routes = [
  {path: '', component: HomeComponent, data: {animation: 'Home'}},
  {path: 'gameSettings', component: PersonalisationComponent, data: {animation: 'Settings'}},
  {path: 'game', component: GameComponent, data: {animation: 'Game'}},
  {path: 'victory', component: VictoryComponent, data: {animation: 'Victory'}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
