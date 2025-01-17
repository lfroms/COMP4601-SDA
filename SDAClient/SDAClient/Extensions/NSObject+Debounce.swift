//
//  NSObject+Debounce.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation

extension NSObject {
    public func debounce(_ task: Selector, after delay: TimeInterval) {
        NSObject.cancelPreviousPerformRequests(withTarget: self, selector: task, object: nil)
        perform(task, with: nil, afterDelay: delay)
    }
}
