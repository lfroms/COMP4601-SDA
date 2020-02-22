//
//  URLSession+CancelAll.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation

extension URLSession {
    internal func cancelAllTasks() {
        getAllTasks { tasks in
            tasks.forEach { task in
                task.cancel()
            }
        }
    }
}
