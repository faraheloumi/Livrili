import { Component, OnInit } from '@angular/core';
import { BuyerOrder } from '../shared/models/BuyerOrder';
import { SellerOrder } from '../shared/models/SellerOrder';
import { OrderItem } from '../shared/models/OrderItem';
import { Product } from '../shared/models/product';
import { Category } from '../shared/models/Category';
import { Account } from '../shared/models/Account';
// Assuming all import paths are correct and align with your project structure

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: BuyerOrder[] = [
    {'id': 1,
  'totalAmount': 447,
  'status': 'Shipped',
  'date': new Date(2023, 3, 13),
  'buyer': {'id': 1,
   'username': 'buyer1',
   'email': 'buyer1@example.com',
   'address': '864 Buyer Street',
   'phoneNumber': '494-164-4196',
   'image': 'path/to/buyer1/image'},
  'sellerOrders': [{'id': 1,
    'totalAmount': 140,
    'status': 'Shipped',
    'date': new Date(2023, 3, 23),
    'seller': {'id': 1,
     'username': 'seller1',
     'email': 'seller1@example.com',
     'address': '826 Seller Lane',
     'phoneNumber': '795-222-3954',
     'image': 'path/to/seller1/image'},
    'orderItems': [{'id': 1,
      'quantity': 3,
      'product': {'id': 1,
       'name': 'Product 1',
       'description': 'A great product',
       'price': 29,
       'quantity': 24,
       'rating': 1.1824698266679396,
       'numberRatings': 76,
       'image': 'path/to/product1/image',
       'category': {'id': 1, 'name': 'Watches'},
       'brand': {'id': 2, 'name': 'BrandD', 'image': 'path/to/brand4/image'},
       'seller': {'id': 1, 'username': 'seller1'}},
      'rating': 3.54530122750387}]}]},
 {'id': 2,
  'totalAmount': 111,
  'status': 'Shipped',
  'date': new Date(2023, 1, 26),
  'buyer': {'id': 2,
   'username': 'buyer2',
   'email': 'buyer2@example.com',
   'address': '534 Buyer Street',
   'phoneNumber': '747-424-1639',
   'image': 'path/to/buyer2/image'},
  'sellerOrders': [{'id': 2,
    'totalAmount': 254,
    'status': 'Shipped',
    'date': new Date(2023, 7, 3, 0, 0),
    'seller': {'id': 2,
     'username': 'seller2',
     'email': 'seller2@example.com',
     'address': '774 Seller Lane',
     'phoneNumber': '634-481-9498',
     'image': 'path/to/seller2/image'},
    'orderItems': [{'id': 2,
      'quantity': 3,
      'product': {'id': 2,
       'name': 'Product 2',
       'description': 'A great product',
       'price': 23,
       'quantity': 73,
       'rating': 1.782383328288764,
       'numberRatings': 6,
       'image': 'path/to/product2/image',
       'category': {'id': 2, 'name': 'Clothes'},
       'brand': {'id': 2, 'name': 'BrandA', 'image': 'path/to/brand1/image'},
       'seller': {'id': 2, 'username': 'seller2'}},
      'rating': 4.486188248435036}]}]},
 {'id': 3,
  'totalAmount': 255,
  'status': 'Shipped',
  'date': new Date(2023, 2, 27),
  'buyer': {'id': 3,
   'username': 'buyer3',
   'email': 'buyer3@example.com',
   'address': '798 Buyer Street',
   'phoneNumber': '817-726-3773',
   'image': 'path/to/buyer3/image'},
  'sellerOrders': [{'id': 3,
    'totalAmount': 119,
    'status': 'Shipped',
    'date': new Date(2023, 3, 15),
    'seller': {'id': 3,
     'username': 'seller3',
     'email': 'seller3@example.com',
     'address': '100 Seller Lane',
     'phoneNumber': '587-910-7378',
     'image': 'path/to/seller3/image'},
    'orderItems': [{'id': 3,
      'quantity': 3,
      'product': {'id': 3,
       'name': 'Product 3',
       'description': 'A great product',
       'price': 199,
       'quantity': 99,
       'rating': 3.163334348094534,
       'numberRatings': 36,
       'image': 'path/to/product3/image',
       'category': {'id': 3, 'name': 'Glasses'},
       'brand': {'id': 1, 'name': 'BrandD', 'image': 'path/to/brand2/image'},
       'seller': {'id': 3, 'username': 'seller3'}},
      'rating': 2.200852569237556}]}]},
 {'id': 4,
  'totalAmount': 312,
  'status': 'Shipped',
  'date': new Date(2023, 11, 28, 0, 0),
  'buyer': {'id': 4,
   'username': 'buyer4',
   'email': 'buyer4@example.com',
   'address': '519 Buyer Street',
   'phoneNumber': '976-525-6630',
   'image': 'path/to/buyer4/image'},
  'sellerOrders': [{'id': 4,
    'totalAmount': 250,
    'status': 'Shipped',
    'date': new Date(2023, 8, 11),
    'seller': {'id': 4,
     'username': 'seller4',
     'email': 'seller4@example.com',
     'address': '957 Seller Lane',
     'phoneNumber': '908-585-7015',
     'image': 'path/to/seller4/image'},
    'orderItems': [{'id': 4,
      'quantity': 3,
      'product': {'id': 4,
       'name': 'Product 4',
       'description': 'A great product',
       'price': 21,
       'quantity': 70,
       'rating': 4.049138131692875,
       'numberRatings': 10,
       'image': 'path/to/product4/image',
       'category': {'id': 4, 'name': 'Shoes'},
       'brand': {'id': 1, 'name': 'BrandB', 'image': 'path/to/brand4/image'},
       'seller': {'id': 4, 'username': 'seller4'}},
      'rating': 4.937464125567235}]}]}
    
    // Additional orders can be added as needed
  ];

  constructor() {}

  ngOnInit(): void {
    // Static orders are already defined
  }

  showMe1: number = -1;
  showMe2: string | null = null;

  toggleTag1(rowIndex: number) {
    this.showMe1 = this.showMe1 === rowIndex ? -1 : rowIndex;
    this.showMe2 = null;
  }

  toggleTag2(rowIndex: number, subRowIndex: number) {
    const indexString = `${rowIndex}-${subRowIndex}`;
    this.showMe2 = this.showMe2 === indexString ? null : indexString;
  }
}
