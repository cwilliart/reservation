import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Creneau } from './creneau.model';
import { CreneauService } from './creneau.service';

@Component({
    selector: 'jhi-creneau-detail',
    templateUrl: './creneau-detail.component.html'
})
export class CreneauDetailComponent implements OnInit, OnDestroy {

    creneau: Creneau;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private creneauService: CreneauService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCreneaus();
    }

    load(id) {
        this.creneauService.find(id).subscribe((creneau) => {
            this.creneau = creneau;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCreneaus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'creneauListModification',
            (response) => this.load(this.creneau.id)
        );
    }
}
