//
//  SearchQuery.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation

final class SearchQuery {
    let terms: String
    
    init(terms: String) {
        self.terms = terms
    }

    internal func formatForLucene() -> String {
        var splitTerms = terms.split(separator: " ")

        splitTerms = splitTerms.compactMap { term in
            "+" + term
        }

        return splitTerms.joined()
    }
}
