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
            List {
                Section(header: ListHeader()) {
                    ForEach(appState.results, id: \.id) { result in
                        NavigationLink(destination: SearchResultDetail(searchResult: result)) {
                            SearchResultListItem(searchResult: result)
                        }
                    }
                }
            }
            .navigationBarItems(trailing: TrailingNavigationBarItems())
            .navigationBarTitle(Text("SDA Search"))
        }
        .sheet(isPresented: $appState.showingSettingsSheet) {
            SettingsSheet()
                .environmentObject(self.appState)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(AppState())
    }
}
