//
//  ContentView.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-11.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject private var appState: AppState

    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                SearchBar()

                List {
                    ForEach(appState.results, id: \.id) { result in
                        SearchResultListItem(searchResult: result)
                    }
                }
            }
            .navigationBarTitle(Text("SDA Search"))
        }
        .colorScheme(.light)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(AppState())
    }
}
