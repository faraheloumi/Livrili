import { Component } from '@angular/core';

interface Service {
  icon: string;
  name: string;
}

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
  services: Service[] = [
    { icon: '../../assets/images/icons/1.png', name: 'Watches' },
    { icon: '../../assets/images/icons/3.png', name: 'Shoes' },
    { icon: '../../assets/images/icons/2.png', name: 'Clothes' },
    { icon: '../../assets/images/icons/4.png', name: 'Glasses' }
  ];
} 
