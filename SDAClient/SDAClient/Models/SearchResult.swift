//
//  SearchResult.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation

struct SearchResult {
    let uuid: UUID = UUID()

    let id: Int
    let name: String
    let url: URL?
    let score: Double
}

extension SearchResult {
    init?(json: [String: Any]) {
        guard let id = json["id"] as? Int,
            let name = json["name"] as? String,
            let url = json["url"] as? String,
            let score = json["score"] as? Double
        else {
            return nil
        }

        self.id = id
        self.name = name
        self.url = URL(string: url)
        self.score = score
    }
}
