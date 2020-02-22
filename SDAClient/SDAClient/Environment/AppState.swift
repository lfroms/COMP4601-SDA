//
//  AppState.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import Foundation
import SwiftUI

final class AppState: NSObject, ObservableObject {
    @Published var searchQuery: String = "" {
        didSet {
            debounce(#selector(fetch), after: 2)
        }
    }
    
    @Published var showingSettingsSheet: Bool = false
    @Published var hostAddress: String = ""
    
    // MARK: - API
    
    @Published var loading: Bool = false
    @Published var results: [SearchResult] = []
    
    // MARK: - API Fetching
    
    private let session: URLSession = URLSession(configuration: .default)
    
    @objc private func fetch() {
        session.cancelAllTasks()
        
        guard !searchQuery.isEmpty else {
            results = []
            return
        }
        
        loading = true
        
        let request = buildRequestFromQuery()
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
    
    private func buildRequestFromQuery() -> URLRequest {
        let query = SearchQuery(terms: searchQuery)
        
        var url = Constants.APIService.defaultBaseUrl
        
        if !hostAddress.isEmpty {
            var components = URLComponents()
            components.scheme = "http"
            components.host = hostAddress
                .replacingOccurrences(of: "http://", with: "")
                .replacingOccurrences(of: "https://", with: "")
            
            components.path = Constants.APIService.appPath
            
            if let newUrl = components.url {
                url = newUrl
            }
        }
        
        url.appendPathComponent(Constants.APIService.searchPath)
        url.appendPathComponent(query.formatForLucene())
        
        var request = URLRequest(url: url)
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Accept")
        
        return request
    }
}
