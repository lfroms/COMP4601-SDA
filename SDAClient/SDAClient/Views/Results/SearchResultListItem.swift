//
//  SearchResultListItem.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SearchResultListItem: View {
    let searchResult: SearchResult

    var body: some View {
        HStack {
            Text(searchResult.name)
            Spacer()
            Text(scoreText)
                .foregroundColor(.secondary)
        }
    }
    
    private var scoreText: String {
        String(searchResult.score)
    }
}

struct SearchResultListItem_Previews: PreviewProvider {
    static var previews: some View {
        SearchResultListItem(
            searchResult: SearchResult(
                id: 1,
                name: "Apple",
                url: URL(string: "http://apple.com")!,
                score: 2.5
            )
        )
    }
}
