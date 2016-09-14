/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

describe('homepage', function () {

    it('should have a title', function () {
        browser.get(browser.baseUrl);

        expect(browser.getTitle()).toContain('play-scalajs-orientdb-seed');
    });

    it('should draw graph', function () {
        expect(element(by.id('cy')).isDisplayed()).toBe(false);

        element(by.name('loadGraphBtn')).click().then(function () {
            expect(element(by.id('cy')).isDisplayed()).toBe(true);
            expect(element(by.id('cy')).getAttribute('data-ng-show')).toContain('graphLoaded');
        });

    });

});