//
//  Constants.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation

final class Constants {
    final class APIService {
        static let baseURL: URL = URL(string: "http://localhost:8080/COMP4601-SDA/rest/sda")!
        static let searchPath: String = "/search"
    }
}
