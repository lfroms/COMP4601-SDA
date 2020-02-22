//
//  SearchBar.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SearchBar: View {
    @EnvironmentObject private var appState: AppState

    var body: some View {
        TextField("Search", text: $appState.searchQuery)
            .padding(.vertical, 7)
            .padding(.horizontal, 14)
            .background(Color.white)
            .cornerRadius(14)
            .padding(.horizontal, 14)
            .disableAutocorrection(true)
            .autocapitalization(.none)
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar()
            .environmentObject(AppState())
    }
}
