import {animate, animateChild, group, query, style, transition, trigger} from "@angular/animations";

export const customAnimation =
  trigger('customAnimations',[
    transition('Home <=> Settings, Settings <=> Game, Game => Victory, Victory => Settings, Victory => Home', [
      // Hide the components
      query(':enter, :leave', [
        style({
          position: 'absolute',
          left: 0,
          width: '100%',
          opacity: 0,
          transform: 'translateY(100%)',
        })
      ]),
      // Animate the new page in
      query(':enter', [
        animate('600ms ease', style({ opacity: 1, transform: 'translateY(0)' })),
      ])
    ]),
  ]);
