//
//  SearchResultDetail.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SearchResultDetail: View {
    var searchResult: SearchResult

    var body: some View {
        List {
            SearchResultDetailItem(label: "ID") {
                Text(String(self.searchResult.id))
            }

            SearchResultDetailItem(label: "Name") {
                Text(self.searchResult.name)
            }

            SearchResultDetailItem(label: "URL") {
                if self.searchResult.url != nil {
                    Button(action: {
                        self.openURL(url: self.searchResult.url!)
                    }) {
                        Text(self.searchResult.url!.absoluteString)
                    }
                }
            }

            SearchResultDetailItem(label: "Score") {
                Text(String(self.searchResult.score))
            }
        }
        .navigationBarTitle(searchResult.name)
    }

    private func openURL(url: URL) {
        UIApplication.shared.open(url)
    }
}

struct SearchResultDetail_Previews: PreviewProvider {
    static var previews: some View {
        SearchResultDetail(
            searchResult: SearchResult(
                id: 1,
                name: "Search Result",
                url: URL(string: "http://apple.com"),
                score: 2.5
            )
        )
    }
}
