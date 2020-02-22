//
//  AppState.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

final class AppState: ObservableObject {
    @Published var searchQuery: String = "" {
        didSet {
            fetch()
        }
    }
    
    // MARK: - API
    
    @Published var loading: Bool = false
    @Published var results: [SearchResult] = []
    
    // MARK: - API Fetching
    
    private let session: URLSession = URLSession(configuration: .default)
    
    internal func fetch() {
        guard !searchQuery.isEmpty else {
            results = []
            return
        }
        
        let query = SearchQuery(terms: searchQuery)
        
        var url = Constants.APIService.baseURL
        url.appendPathComponent(Constants.APIService.searchPath)
        url.appendPathComponent(query.formatForLucene())
        
        var request = URLRequest(url: url)
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Accept")
        
        loading = true
        
        session.dataTask(with: request) { data, _, _ in
            var searchResults: [SearchResult] = []
            
            if let data = data,
                let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [[String: Any]] {
                for case let result in json {
                    if let searchResult = SearchResult(json: result) {
                        searchResults.append(searchResult)
                    }
                }
            }
            
            DispatchQueue.main.async {
                self.results = searchResults
                self.loading = false
            }
            
        }.resume()
    }
}
