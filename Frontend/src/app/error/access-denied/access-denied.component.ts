import { Component } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-access-denied',
  templateUrl: './access-denied.component.html',
  styleUrls: ['../error.component.scss'],
  animations: [routerTransition()]
})
export class AccessDeniedComponent {

  constructor() { }

}
