import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {PersonalisationComponent} from './personalisation/personalisation.component';
import {GameComponent} from './game/game.component';
import {VictoryComponent} from './victory/victory.component';


const routes: Routes = [
  {path: '', component: HomeComponent, data: {animation: 'HomePage'}},
  {path: 'gameSettings', component: PersonalisationComponent, data: { animation: 'PersonalisationPage'}},
  {path: 'game', component: GameComponent},
  {path: 'victory', component: VictoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
